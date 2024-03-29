package com.polendina.kabular.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.polendina.kabular.domain.model.Months
import com.polendina.kabular.utils.initialCapital
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDatePicker(
    modifier: Modifier = Modifier,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onDateSelected: (LocalDate) -> Unit,
    scaffoldContent: @Composable () -> Unit
) {
    var selectedYear: Int by remember { mutableIntStateOf(2010) }
    var selectedMonth: Months by remember { mutableStateOf(Months.JANUARY) }
    BottomSheetScaffold(
//        onDismissRequest = onDismissRequest,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = "Choose Date",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    InfiniteCircularList(
                        // TODO: make the width more proportional!
                        width = 150.dp,
                        itemHeight = 70.dp,
                        items = (2000..2050).toList(),
                        initialItem = 2010,
                        textStyle = TextStyle(fontSize = 23.sp),
                        textColor = Color.LightGray,
                        selectedTextColor = Color.Black,
                        onItemSelected = { i, item ->
                            selectedYear = item
                        }
                    )
                    InfiniteCircularList(
                        width = 150.dp,
                        itemHeight = 70.dp,
                        items = Months.entries.map { it.name.initialCapital() },
                        initialItem = "January",
                        textStyle = TextStyle(fontSize = 23.sp),
                        textColor = Color.LightGray,
                        selectedTextColor = Color.Black,
                        onItemSelected = { i, item ->
                            selectedMonth = Months.valueOf(item.uppercase())
                        }
                    )
                }
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            onDateSelected(LocalDate.of(
                                selectedYear,
                                Months.values().indexOf(selectedMonth) + 1,
                                1
                            ))
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.outline
                        )
                    ) {
                        Text(text = "Select")
                    }
                }
            }
        }
    ) {
        scaffoldContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun BottomSheetDatePickerPreview() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheetDatePicker(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        onDateSelected = {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        },
    ) {
        Button(onClick = {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }) {
            Text(text = "Show")
        }
    }
}