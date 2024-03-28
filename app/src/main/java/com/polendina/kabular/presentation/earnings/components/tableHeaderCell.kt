package com.polendina.kabular.presentation.earnings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TableHeaderCell(
    modifier: Modifier = Modifier,
    headerTitle: String,
    editCallback: () -> Unit
) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.secondary) // primary, secondary,
            .border(1.dp, Color.White)
            .height(40.dp)
            .clickable { editCallback() }
    ) {
        Text(
            text = headerTitle,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}