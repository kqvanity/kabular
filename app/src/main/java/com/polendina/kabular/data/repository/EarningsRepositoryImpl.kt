package com.polendina.kabular.data.repository

import android.app.Application
import com.polendina.kabular.data.database.KabularDatabase
import com.polendina.kabular.data.database.model.TableHeader
import com.polendina.kabular.domain.mapper.asEntity
import com.polendina.kabular.domain.mapper.asEtity
import com.polendina.kabular.domain.mapper.asModel
import com.polendina.kabular.domain.model.Month
import com.polendina.kabular.domain.model.Transaction
import com.polendina.kabular.domain.repository.EarningsRepository

class EarningsRepositoryImpl(
    private val application: Application
): EarningsRepository {
    private val kabularDatabase: KabularDatabase = KabularDatabase.getDatabase(context = application)
    override suspend fun insertMonth(month: Month) {
        kabularDatabase.kabularDao.insertMonth(monthEntity = month.asEntity())
    }
    override suspend fun getMonths(): List<Month> {
        return kabularDatabase.kabularDao.getMonths().map { it.asModel() }
    }
    override suspend fun getTransactions(month: Month): List<Transaction> {
        // TODO: Maybe implement a yearly filter or so?
        return kabularDatabase.kabularDao.getMonthWithTransactions(monthIndex = month.monthIndex)
            .firstOrNull()
            ?.transactionEntities
            ?.map {
                it.asModel()
            } ?: emptyList()
    }
    override suspend fun getHeaders(): List<TableHeader> {
        return kabularDatabase.kabularDao.getTableHeaders()
    }
    override suspend fun insertTransaction(transaction: Transaction) {
        kabularDatabase.kabularDao.insertTransaction(transactionEntity = transaction.asEtity())
    }
    override suspend fun insertHeader(tableHeader: TableHeader) {
        kabularDatabase.kabularDao.insertHeaders(tableHeader = tableHeader)
    }
}