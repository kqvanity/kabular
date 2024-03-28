package com.polendina.kabular.domain.mapper

import com.polendina.kabular.data.database.model.TransactionEntity
import com.polendina.kabular.domain.model.Transaction

fun TransactionEntity.asModel() = Transaction(
    day = day,
    monthIndex = monthIndex,
    earnings = earnings,
    expenditure = expenditure
)
fun Transaction.asEtity() = TransactionEntity(
    day = day,
    monthIndex = monthIndex,
    earnings = earnings,
    expenditure = expenditure
)
