package com.polendina.kabular.domain.use_case

data class UseCases(
    val getTransactions: GetTransactions,
    val insertTransaction: InsertTransaction,
    val delTransaction: DelTransaction,
    val getHeaders: GetHeaders,
    val editHeader: EditHeader,
    val insertMonth: InsertMonth,
    val getMonths: GetMonths
)