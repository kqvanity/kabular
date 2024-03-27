package com.polendina.kabular.presentation.earnings

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.polendina.kabular.domain.repository.Transaction
import com.polendina.kabular.domain.use_case.UseCases
import kotlin.random.Random

interface EarningsViewModel {
   val headers: SnapshotStateList<String>
   val rows: SnapshotStateList<Transaction>
}

class EarningsViewModelImpl(
   private val useCases: UseCases
): ViewModel(), EarningsViewModel {
   override val headers: SnapshotStateList<String> = mutableStateListOf("Earnings", "Expenditure", "Profit", "Proportion", "State")
   override val rows: SnapshotStateList<Transaction> = (1..90).map {
      Transaction(earnings = Random.nextLong(0,100), expenditure = Random.nextLong(0, 100))
   }.toMutableStateList()
}