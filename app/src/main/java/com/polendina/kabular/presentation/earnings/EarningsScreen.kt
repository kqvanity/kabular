package com.polendina.kabular.presentation.earnings

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.polendina.kabular.domain.repository.Transaction
import kotlin.random.Random

@Composable
fun Table(
    modifier: Modifier = Modifier,
    rowCount: Int,
    columnCount: Int,
    stickyRowCount: Int = 0,
    stickyColumnCount: Int = 0,
    maxCellWidthDp: Dp = Dp.Infinity,
    maxCellHeightDp: Dp = Dp.Infinity,
    verticalScrollState: ScrollState = rememberScrollState(),
    horizontalScrollState: ScrollState = rememberScrollState(),
    cellContent: @Composable (rowIndex: Int, columnIndex: Int) -> Unit
) {
    val columnWidths = remember { mutableStateMapOf<Int, Int>() }
    val rowHeights = remember { mutableStateMapOf<Int, Int>() }

    val maxCellWidth = if (listOf(Dp.Infinity, Dp.Unspecified).contains(maxCellWidthDp)) {
        Constraints.Infinity
    } else {
        with(LocalDensity.current) { maxCellWidthDp.toPx() }.toInt()
    }
    val maxCellHeight = if (listOf(Dp.Infinity, Dp.Unspecified).contains(maxCellHeightDp)) {
        Constraints.Infinity
    } else {
        with(LocalDensity.current) { maxCellHeightDp.toPx() }.toInt()
    }

    var accumWidths = mutableListOf<Int>()
    var accumHeights = mutableListOf<Int>()

    @Composable
    fun StickyCells(modifier: Modifier = Modifier, rowCount: Int, columnCount: Int) {
        if (rowCount > 0 && columnCount > 0) {
            Box(modifier = modifier) {
                Layout(
                    content = {
                        (0 until rowCount).forEach { rowIndex ->
                            (0 until columnCount).forEach { columnIndex ->
                                cellContent(rowIndex = rowIndex, columnIndex = columnIndex)
                            }
                        }
                    },
                ) { measurables, constraints ->
                    val placeables = measurables.mapIndexed { index, it ->
                        val columnIndex = index % columnCount
                        val rowIndex = index / columnCount
                        it.measure(
                            Constraints(
                                minWidth = columnWidths[columnIndex] ?: 0,
                                minHeight = rowHeights[rowIndex] ?: 0,
                                maxWidth = columnWidths[columnIndex] ?: 0,
                                maxHeight = rowHeights[rowIndex] ?: 0
                            )
                        )
                    }

                    val totalWidth = accumWidths[columnCount]
                    val totalHeight = accumHeights[rowCount]

                    layout(width = totalWidth, height = totalHeight) {
                        placeables.forEachIndexed { index, placeable ->
                            val columnIndex = index % columnCount
                            val rowIndex = index / columnCount

                            placeable.placeRelative(
                                accumWidths[columnIndex],
                                accumHeights[rowIndex]
                            )
                        }
                    }
                }
            }
        }
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .then(Modifier.horizontalScroll(horizontalScrollState))
                .then(Modifier.verticalScroll(verticalScrollState))
        ) {
            Layout(
                content = {
                    (0 until rowCount).forEach { rowIndex ->
                        (0 until columnCount).forEach { columnIndex ->
                            cellContent(rowIndex = rowIndex, columnIndex = columnIndex)
                        }
                    }
                },
            ) { measurables, constraints ->
                val placeables = measurables.mapIndexed { index, it ->
                    val columnIndex = index % columnCount
                    val rowIndex = index / columnCount
                    it.measure(
                        Constraints(
                            minWidth = columnWidths[columnIndex] ?: 0,
                            minHeight = rowHeights[rowIndex] ?: 0,
                            maxWidth = maxCellWidth,
                            maxHeight = maxCellHeight
                        )
                    )
                }

                placeables.forEachIndexed { index, placeable ->
                    val columnIndex = index % columnCount
                    val rowIndex = index / columnCount

                    val existingWidth = columnWidths[columnIndex] ?: 0
                    val maxWidth = maxOf(existingWidth, placeable.width)
                    if (maxWidth > existingWidth) {
                        columnWidths[columnIndex] = maxWidth
                    }

                    val existingHeight = rowHeights[rowIndex] ?: 0
                    val maxHeight = maxOf(existingHeight, placeable.height)
                    if (maxHeight > existingHeight) {
                        rowHeights[rowIndex] = maxHeight
                    }
                }

                accumWidths = mutableListOf(0).apply {
                    (1..columnWidths.size).forEach { i ->
                        this += this.last() + columnWidths[i - 1]!!
                    }
                }
                accumHeights = mutableListOf(0).apply {
                    (1..rowHeights.size).forEach { i ->
                        this += this.last() + rowHeights[i - 1]!!
                    }
                }

                val totalWidth = accumWidths.last()
                val totalHeight = accumHeights.last()

                layout(width = totalWidth, height = totalHeight) {
                    placeables.forEachIndexed { index, placeable ->
                        val columnIndex = index % columnCount
                        val rowIndex = index / columnCount

                        placeable.placeRelative(accumWidths[columnIndex], accumHeights[rowIndex])
                    }
                }
            }
        }

        StickyCells(
            modifier = Modifier.horizontalScroll(horizontalScrollState),
            rowCount = stickyRowCount,
            columnCount = columnCount
        )

        StickyCells(
            modifier = Modifier.verticalScroll(verticalScrollState),
            rowCount = rowCount,
            columnCount = stickyColumnCount
        )

        StickyCells(
            rowCount = stickyRowCount,
            columnCount = stickyColumnCount
        )
    }
}

@Composable
fun TableHeaderCell(
    modifier: Modifier = Modifier,
    headerTitle: String,
) {
    Box (
        modifier = Modifier
            .background(Color.Red)
            .border(1.dp, Color.White)
            .clickable { }
    ) {
        Text(text = headerTitle)
    }
}

@Composable
fun TableCell(
    modifier: Modifier = Modifier,
    transaction: Number
) {
    Box (
        modifier = Modifier
            .background(Color.Red)
            .border(1.dp, Color.White)
    ) {
        Text(
            text = transaction.toString(),
        )
    }
}

enum class Columns {
    FIRST,
    SECOND,
    THIRD,
    FOURTH
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun TablePreview() {
    val headers: SnapshotStateList<String> = remember { mutableStateListOf("Earnings", "Expenditure", "Profit", "Proportion") }
    val rows: SnapshotStateList<Transaction> = remember { (1..90).map {
        Transaction(earnings = Random.nextLong(0, 100), expenditure = Random.nextLong(0, 100))
    }.toMutableStateList()}
    Scaffold {
        Table(
            rowCount = rows.size,
            columnCount = headers.size
        ) { rowIndex, columnIndex ->
            if (rowIndex == 0) {
                TableHeaderCell(headerTitle = headers[columnIndex])
            } else {
                rows.getOrNull(columnIndex)?.let { transaction ->
                    when(Columns.values().get(columnIndex)) {
                        Columns.FIRST -> TableCell(transaction = transaction.earnings)
                        Columns.SECOND -> TableCell(transaction = transaction.expenditure)
                        Columns.THIRD -> TableCell(transaction = transaction.profit)
                        Columns.FOURTH -> TableCell(transaction = transaction.growthProportion)
                    }
                }
            }
        }
    }
}