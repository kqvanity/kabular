package com.polendina.kabular.presentation.earnings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
private fun TableCell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(1.dp, Color.White)
            .width(90.dp)
            .height(40.dp)
    ) {
        content()
    }
}

@Composable
fun DigitalTableCell(
    transaction: Number,
) {
    TableCell (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            // FIXME: Clipping at the very right of the cells next to the left of latest column aren't pixel-perfect!
            .clip(RoundedCornerShape(10.dp))
    ) {
        Text(
            text = transaction.toString(),
        )
    }
}

@Composable
fun BooleanTableCell(
    state: Boolean,
) {
    val title = if (state) "Gain" else "Loss"
    val backgroundColor = if (state) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.error
    val textColor = if (state) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onError
    TableCell (
        modifier = Modifier
            .padding(start = 1.dp)
            .background(backgroundColor)
    ) {
        Text(
            // TODO: Use string resource if there's time left
            text = title,
            style = TextStyle(
                color = textColor,
            ),
            overflow = TextOverflow.Clip
        )
    }
}