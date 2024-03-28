package com.polendina.kabular.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = false)
    val day: Int,
    val monthIndex: Int,
    val earnings: Long,
    val expenditure: Long
)