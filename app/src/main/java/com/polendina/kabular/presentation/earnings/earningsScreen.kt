package com.polendina.kabular.presentation.earnings

import android.widget.Toast
import androidx.annotation.IntRange
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polendina.kabular.domain.model.Columns
import com.polendina.kabular.domain.model.Transaction
import com.polendina.kabular.presentation.components.AppBottomNavigationBar
import com.polendina.kabular.presentation.components.AppTopBar
import com.polendina.kabular.presentation.earnings.components.BooleanTableCell
import com.polendina.kabular.presentation.earnings.components.DigitalTableCell
import com.polendina.kabular.presentation.earnings.components.EditTableHeaderDialog
import com.polendina.kabular.presentation.earnings.components.Table
import com.polendina.kabular.presentation.earnings.components.TableHeaderCell
import com.polendina.kabular.utils.isNotEmptyNorBlank
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun EarningsScreen(
    modifier: Modifier = Modifier,
    earningsViewModel: EarningsViewModel
) {
    val localContext = LocalContext.current
    Scaffold (
        topBar = {
            AppTopBar(
                undefinedBehaviorCallback = {
                    Toast.makeText(localContext, "Behavior still not implemented!", Toast.LENGTH_SHORT).show()
                }
            )
        },
        bottomBar = {
            AppBottomNavigationBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    earningsViewModel.prepopulateDummyData()
                }
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
        }
    ) { padding ->
        Table(
            rowCount = earningsViewModel.rows.size,
            columnCount = earningsViewModel.headers.size,
            modifier = modifier
                .padding(padding)
                .padding(top = 1.dp)
        ) { rowIndex, columnIndex ->
            if (rowIndex == 0) {
                TableHeaderCell(
                    headerTitle = earningsViewModel.headers[columnIndex],
                    editCallback = {
                        earningsViewModel.updateCurrentHeaderIndex(columnIndex)
                        earningsViewModel.showEditTableColumnHeaderDialog = true
                    }
                )
            } else {
                earningsViewModel.rows.getOrNull(rowIndex)?.let { transaction ->
                    when(Columns.values()[columnIndex]) {
                        Columns.FIRST -> DigitalTableCell(transaction = rowIndex)
                        Columns.SECOND -> DigitalTableCell(transaction = transaction.earnings)
                        Columns.THIRD -> DigitalTableCell(transaction = transaction.expenditure)
                        Columns.FOURTH -> DigitalTableCell(transaction = transaction.profit)
                        Columns.FIFTH -> DigitalTableCell(transaction = transaction.growthProportion)
                        Columns.SIXTH -> BooleanTableCell(state = transaction.loss)
                    }
                }
            }
        }
        AnimatedVisibility(visible = earningsViewModel.showEditTableColumnHeaderDialog) {
            // FIXME: When the dialog appears, the edit field doesn't have focus by default.
            var invalidHeaderTitle: Boolean by remember { mutableStateOf(false) }
            EditTableHeaderDialog(
                currentHeader = earningsViewModel.currentHeader,
                onDismissRequest = {
                    earningsViewModel.showEditTableColumnHeaderDialog = false
                },
                onHeaderValueChange = {
                    earningsViewModel.currentHeader = it
                },
                onAcceptNewHeader = {
                    invalidHeaderTitle = !earningsViewModel.updateHeader(earningsViewModel.currentHeader)
                },
                invalidHeaderTitle = invalidHeaderTitle,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun TablePreview() {
    EarningsScreen(
        earningsViewModel = object : EarningsViewModel {
            override val headers: SnapshotStateList<String> = remember { mutableStateListOf() }
            override val rows: SnapshotStateList<Transaction> = remember { mutableStateListOf() }
            override var currentHeader: String by remember { mutableStateOf("") }
            override var currentHeaderIndex: Int by remember { mutableIntStateOf(0) }
            // TODO: I'll duplicate the implementation for now till I start using use cases!
            override fun updateHeader(newHeaderTitle: String): Boolean {
                if (newHeaderTitle.isNotEmptyNorBlank()) {
                    headers[currentHeaderIndex] = newHeaderTitle
                    showEditTableColumnHeaderDialog = false
                    return (true)
                } else {
                    return (false)
                }
            }
            val coroutineScope = rememberCoroutineScope()
            override fun prepopulateDummyData(): Job = coroutineScope.launch {
                headers.addAll(listOf("Day", "Earnings", "Expenditure", "Profit", "Proportion", "State"))
                rows.addAll((1..32).map {
                    Transaction(day = it, monthIndex = Random.nextInt(from = 1, until = 12), earnings = Random.nextLong(0,100), expenditure = Random.nextLong(0, 100))
                }.toMutableStateList())
            }
            override var showEditTableColumnHeaderDialog: Boolean by remember { mutableStateOf(false) }
            override fun updateCurrentHeaderIndex(@IntRange(from = 0L, to = 5) newIndex: Int) {
                currentHeaderIndex = newIndex
                currentHeader = headers[currentHeaderIndex]
            }
        }
    )
}