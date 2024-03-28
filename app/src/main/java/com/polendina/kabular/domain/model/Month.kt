package com.polendina.kabular.domain.model

data class Month(
    val monthIndex: Int,
) {
    val name: String
        get() = Months.entries.get(monthIndex).name
}
