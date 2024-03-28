package com.polendina.kabular.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "month")
data class MonthEntity(
    @PrimaryKey(autoGenerate = false)
    val monthIndex: Int,
)