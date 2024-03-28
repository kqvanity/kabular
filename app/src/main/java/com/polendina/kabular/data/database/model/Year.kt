package com.polendina.kabular.data.database.model

import androidx.room.Entity

@Entity(tableName = "years")
data class Year(
    val year: Long,
)