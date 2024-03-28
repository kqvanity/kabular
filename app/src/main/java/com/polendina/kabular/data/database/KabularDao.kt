package com.polendina.kabular.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.polendina.kabular.data.database.model.TableHeader
import com.polendina.kabular.data.database.model.TransactionEntity
import com.polendina.kabular.data.database.model.MonthEntity
import com.polendina.kabular.data.database.model.MonthWithTransactions

@Dao
interface KabularDao {
    @Query("select * from table_header")
    suspend fun getTableHeaders(): List<TableHeader>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeaders(tableHeader: TableHeader)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonth(monthEntity: MonthEntity)
    // TODO: Maybe add a way to get months by year?
    @Query("select * FROM month")
    suspend fun getMonths(): List<MonthEntity>
    @androidx.room.Transaction
    @Query("SELECT * FROM month WHERE monthIndex == :monthIndex")
    suspend fun getMonthWithTransactions(monthIndex: Int): List<MonthWithTransactions>
}