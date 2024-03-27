package com.polendina.kabular.domain.use_case

data class UseCases(
    val getTransactions: getTransactions,
    val addTransaction: addTransaction,
    val delTransaction: DelTransaction,
    val getHeaders: getHeaders,
    val editHeader: EditHeader
)
class getHeaders
class EditHeader