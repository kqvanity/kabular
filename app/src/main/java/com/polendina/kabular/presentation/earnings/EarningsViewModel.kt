package com.polendina.kabular.presentation.earnings

import android.app.Application
import androidx.annotation.IntRange
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.polendina.kabular.R
import com.polendina.kabular.data.database.model.TableHeader
import com.polendina.kabular.data.database.model.TransactionEntity
import com.polendina.kabular.domain.mapper.asModel
import com.polendina.kabular.domain.model.Month
import com.polendina.kabular.domain.model.Transaction
import com.polendina.kabular.domain.use_case.UseCases
import com.polendina.kabular.utils.isNotEmptyNorBlank
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

interface EarningsViewModel {
   val headers: List<String>
   val rows: List<Transaction>
   val currentHeaderIndex: Int
   var currentHeader: String
   val currentDate: LocalDate
   var showEditTableColumnHeaderDialog: Boolean
   fun updateDate(localDate: LocalDate): Unit
   fun updateHeader(newHeaderTitle: String): Boolean
   fun updateCurrentHeaderIndex(newIndex: Int): Unit
   fun prepopulateDummyData(): Job
}

class EarningsViewModelImpl(
   private val useCases: UseCases,
   val application: Application
): AndroidViewModel(application = application), EarningsViewModel {
   override val headers: SnapshotStateList<String> = mutableStateListOf()
   // TODO: This should be triggered by user or something?
   override val rows: SnapshotStateList<Transaction> = mutableStateListOf()
   override var currentDate: LocalDate by mutableStateOf(LocalDate.of(2010, 1, 1))
   override var currentHeader by mutableStateOf("")
   override var currentHeaderIndex: Int by mutableIntStateOf(0)
   override fun updateCurrentHeaderIndex(@IntRange(from = 0L, to = 5) newIndex: Int) {
      currentHeaderIndex = newIndex
      currentHeader = headers[currentHeaderIndex]
   }
   override var showEditTableColumnHeaderDialog by mutableStateOf(false)
   override fun updateDate(localDate: LocalDate): Unit {
      // TODO: LocalDate checks and whatnot!
      currentDate = localDate
      viewModelScope.launch {
         updateTransactionsDate()
      }
   }
   override fun updateHeader(newHeaderTitle: String): Boolean {
      if (newHeaderTitle.isNotEmptyNorBlank()) {
         headers[currentHeaderIndex] = newHeaderTitle.trim()
         showEditTableColumnHeaderDialog = false
         viewModelScope.launch {
            useCases.editHeader(tableHeader = TableHeader(title = newHeaderTitle.trim(), index = currentHeaderIndex))
         }
         return (true)
      } else {
         return (false)
      }
   }

   // TODO: Prepopulate the database with dummy data!
   override fun prepopulateDummyData(): Job = viewModelScope.launch {
      listOf(
         application.resources.getString(R.string.Day), application.resources.getString(R.string.Earnings),
         application.resources.getString(R.string.Expenditure), application.resources.getString( R.string.Profit),
         application.resources.getString(R.string.Proportion), application.resources.getString(R.string.State)
      ).forEachIndexed { index, headerTitle->
         useCases.editHeader(tableHeader = TableHeader(title = headerTitle, index = index))
      }
      useCases.insertMonth(Month(monthIndex = currentDate.month.value))
      (1..30).map {
         TransactionEntity(
            day = it,
            monthIndex = currentDate.month.value,
            earnings = Random.nextLong(0,100),
            expenditure = Random.nextLong(0, 100)
         )
      }.forEach {
         useCases.insertTransaction(transaction = it.asModel())
      }
      updateTransactionsDate()
   }

   suspend fun updateTransactionsDate() {
      headers.clear()
      useCases.getHeaders().let {
         headers.addAll(it.map { it.title })
      }
      println("INFO" + currentDate.month.value)
      rows.clear()
      rows.addAll(useCases.getTransactions(month = Month(monthIndex = currentDate.month.value)))
   }
   init {
      viewModelScope.launch {
         updateTransactionsDate()
      }
   }
}