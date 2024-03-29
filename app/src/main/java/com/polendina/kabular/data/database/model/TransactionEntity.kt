package com.polendina.kabular.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    val day: Int,
    val monthIndex: Int,
    @PrimaryKey(autoGenerate = false)
    val id: String = monthIndex.toString().plus(day),
    val earnings: Long,
    val expenditure: Long
)