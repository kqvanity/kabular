package com.polendina.kabular.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.polendina.kabular.data.database.model.MonthEntity
import com.polendina.kabular.data.database.model.TableHeader
import com.polendina.kabular.data.database.model.TransactionEntity

@Database(
    entities = [MonthEntity::class, TableHeader::class, TransactionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class KabularDatabase: RoomDatabase() {
    abstract val kabularDao: KabularDao
    companion object {
        @Volatile
        private var INSTANCE: KabularDatabase? = null
        fun getDatabase(context: Context): KabularDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context,
                    klass = KabularDatabase::class.java,
                    name = "word"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}