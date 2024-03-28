package com.polendina.kabular.domain.repository

import com.polendina.kabular.data.database.model.TableHeader
import com.polendina.kabular.domain.model.Month
import com.polendina.kabular.domain.model.Transaction

interface EarningsRepository {
    suspend fun insertTransaction(transaction: Transaction): Unit
    suspend fun getHeaders(): List<TableHeader>
    suspend fun insertHeader(tableHeader: TableHeader): Unit
    suspend fun insertMonth(month: Month): Unit
    suspend fun getTransactions(month: Month): List<Transaction>
    suspend fun getMonths(): List<Month>
}