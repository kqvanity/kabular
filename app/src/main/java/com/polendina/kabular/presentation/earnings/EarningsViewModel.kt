package com.polendina.kabular.presentation.earnings

import androidx.annotation.IntRange
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.polendina.kabular.domain.repository.Transaction
import com.polendina.kabular.domain.use_case.UseCases
import com.polendina.kabular.utils.isNotEmptyNorBlank
import kotlin.random.Random

interface EarningsViewModel {
   val headers: List<String>
   val rows: List<Transaction>
   val currentHeaderIndex: Int
   var currentHeader: String
   var showEditTableColumnHeaderDialog: Boolean
   fun updateHeader(newHeaderTitle: String): Boolean
   fun updateCurrentHeaderIndex(newIndex: Int): Unit
}

class EarningsViewModelImpl(
   private val useCases: UseCases
): ViewModel(), EarningsViewModel {
   override val headers: SnapshotStateList<String> = mutableStateListOf("Earnings", "Expenditure", "Profit", "Proportion", "State")
   override val rows: SnapshotStateList<Transaction> = (1..90).map {
      Transaction(earnings = Random.nextLong(0,100), expenditure = Random.nextLong(0, 100))
   }.toMutableStateList()

   override var currentHeader by mutableStateOf("")
   override var currentHeaderIndex: Int by mutableIntStateOf(0)
   override fun updateCurrentHeaderIndex(@IntRange(from = 0L, to = 5) newIndex: Int) {
      currentHeaderIndex = newIndex
      currentHeader = headers[currentHeaderIndex]
   }
   override var showEditTableColumnHeaderDialog by mutableStateOf(true)
   override fun updateHeader(newHeaderTitle: String): Boolean {
      if (!newHeaderTitle.isNotEmptyNorBlank()) {
         headers[currentHeaderIndex] = newHeaderTitle
         showEditTableColumnHeaderDialog = false
         return (true)
      } else {
         return (false)
      }
   }
}