package com.polendina.kabular.data.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class MonthWithTransactions(
    @Embedded val monthEntity: MonthEntity,
    @Relation(
        parentColumn = "monthIndex",
        entityColumn = "monthIndex"
    )
    val transactionEntities: List<TransactionEntity>
)