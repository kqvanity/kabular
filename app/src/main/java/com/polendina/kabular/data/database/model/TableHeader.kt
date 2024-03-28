package com.polendina.kabular.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_header")
data class TableHeader(
    val title: String,
    @PrimaryKey
    val index: Int
)