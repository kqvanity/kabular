package com.polendina.kabular.domain.mapper

import com.polendina.kabular.data.database.model.MonthEntity
import com.polendina.kabular.domain.model.Month

fun Month.asEntity() = MonthEntity(
    monthIndex = monthIndex
)

fun MonthEntity.asModel() = Month(
    monthIndex = monthIndex
)