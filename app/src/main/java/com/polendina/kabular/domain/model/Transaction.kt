package com.polendina.kabular.domain.model

import androidx.annotation.IntRange
import com.polendina.kabular.utils.roundTo

data class Transaction(
    @IntRange(from = 1L, to = 32L)
    val day: Int,
    @IntRange(from = 1, to = 12L)
    val monthIndex: Int,
    val earnings: Long,
    val expenditure: Long
) {
    val profit: Long
        get() = earnings - expenditure
    val growthProportion: Double
        get() = (earnings.toDouble() / if (expenditure == 0L) 1 else expenditure).roundTo(2)
    val loss: Boolean
        get() = profit < 0
}