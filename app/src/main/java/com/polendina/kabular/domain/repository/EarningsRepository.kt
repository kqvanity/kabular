package com.polendina.kabular.domain.repository

import com.polendina.kabular.utils.roundTo

interface EarningsRepository {
    fun getTransactions(): List<Transaction>
}
data class Transaction(
    val earnings: Long,
    val expenditure: Long
) {
    val profit: Long
        get() = earnings - expenditure
    val growthProportion: Double
        get() = (earnings.toDouble() / expenditure).roundTo(2)
    val loss: Boolean
        get() = profit < 0
}