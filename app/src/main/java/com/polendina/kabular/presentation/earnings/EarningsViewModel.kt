package com.polendina.kabular.presentation.earnings

import androidx.annotation.IntRange
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polendina.kabular.data.database.model.TableHeader
import com.polendina.kabular.data.database.model.TransactionEntity
import com.polendina.kabular.domain.mapper.asModel
import com.polendina.kabular.domain.model.Month
import com.polendina.kabular.domain.model.Transaction
import com.polendina.kabular.domain.use_case.UseCases
import com.polendina.kabular.utils.isNotEmptyNorBlank
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.random.Random

interface EarningsViewModel {
   val headers: List<String>
   val rows: List<Transaction>
   val currentHeaderIndex: Int
   var currentHeader: String
   var showEditTableColumnHeaderDialog: Boolean
   fun updateHeader(newHeaderTitle: String): Boolean
   fun updateCurrentHeaderIndex(newIndex: Int): Unit
   fun prepopulateDummyData(): Job
}

class EarningsViewModelImpl(
   private val useCases: UseCases
): ViewModel(), EarningsViewModel {
   override val headers: SnapshotStateList<String> = mutableStateListOf()
   // TODO: This should be triggered by user or something?
   override val rows: SnapshotStateList<Transaction> = mutableStateListOf()

   override var currentHeader by mutableStateOf("")
   override var currentHeaderIndex: Int by mutableIntStateOf(0)
   override fun updateCurrentHeaderIndex(@IntRange(from = 0L, to = 5) newIndex: Int) {
      currentHeaderIndex = newIndex
      currentHeader = headers[currentHeaderIndex]
   }
   override var showEditTableColumnHeaderDialog by mutableStateOf(false)
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
      rows.clear()
      listOf("Day", "Earnings", "Expenditure", "Profit", "Proportion", "State").forEachIndexed { index, headerTitle->
         useCases.editHeader(tableHeader = TableHeader(title = headerTitle, index = index))
      }
      useCases.insertMonth(Month(monthIndex = 1))
      headers.clear()
      (1..30).map {
         TransactionEntity(day = it, monthIndex = 1, earnings = Random.nextLong(0,100), expenditure = Random.nextLong(0, 100))
      }.forEach {
         useCases.insertTransaction(transaction = it.asModel())
      }
      useCases.getTransactions(month = Month(monthIndex = 1)).forEach {
         rows.add(it)
      }
      useCases.getHeaders.invoke().let {
         headers.addAll(it.map { it.title })
      }
   }

   init {
      viewModelScope.launch {
         useCases.getTransactions(month = Month(monthIndex = 1)).forEach {
            rows.add(it)
         }
         useCases.getHeaders.invoke().let {
            headers.addAll(it.map { it.title })
         }
      }
   }
}