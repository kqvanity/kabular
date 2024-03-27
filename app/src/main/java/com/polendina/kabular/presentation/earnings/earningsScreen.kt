package com.polendina.kabular.presentation.earnings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.polendina.kabular.domain.repository.Transaction
import com.polendina.kabular.presentation.components.AppBottomNavigationBar
import com.polendina.kabular.presentation.components.AppTopBar
import com.polendina.kabular.presentation.earnings.components.BooleanTableCell
import com.polendina.kabular.presentation.earnings.components.DigitalTableCell
import com.polendina.kabular.presentation.earnings.components.Table
import com.polendina.kabular.presentation.earnings.components.TableHeaderCell
import kotlin.random.Random

@Composable
fun EarningsScreen(
    modifier: Modifier = Modifier,
    earningsViewModel: EarningsViewModel
) {
    Scaffold (
        topBar = {
            AppTopBar()
        },
        bottomBar = {
            AppBottomNavigationBar()
        }
    ) { padding ->
        Table(
            rowCount = earningsViewModel.rows.size,
            columnCount = earningsViewModel.headers.size,
            modifier = modifier
                .padding(padding)
        ) { rowIndex, columnIndex ->
            if (rowIndex == 0) {
                TableHeaderCell(
                    headerTitle = earningsViewModel.headers[columnIndex],
                    editCallback = {
//                        earningsViewModel.headers[columnIndex] = "More"
                    }
                )
            } else {
                earningsViewModel.rows.getOrNull(rowIndex)?.let { transaction ->
                    when(Columns.values()[columnIndex]) {
                        Columns.FIRST -> DigitalTableCell(transaction = transaction.earnings)
                        Columns.SECOND -> DigitalTableCell(transaction = transaction.expenditure)
                        Columns.THIRD -> DigitalTableCell(transaction = transaction.profit)
                        Columns.FOURTH -> DigitalTableCell(transaction = transaction.growthProportion)
                        Columns.FIFTH -> BooleanTableCell(state = transaction.loss)
                    }
                }
            }
        }
    }
}

enum class Columns {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun TablePreview() {
    EarningsScreen(
        earningsViewModel = object : EarningsViewModel {
            override val headers: SnapshotStateList<String> = remember { mutableStateListOf("Earnings", "Expenditure", "Profit", "Proportion", "State") }
            override val rows: SnapshotStateList<Transaction> = remember { (1..90).map {
                Transaction(earnings = Random.nextLong(0,100), expenditure = Random.nextLong(0, 100))
            }.toMutableStateList()}
        }
    )
}