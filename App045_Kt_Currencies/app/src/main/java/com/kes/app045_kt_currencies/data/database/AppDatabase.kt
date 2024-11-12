package com.kes.app045_kt_currencies.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kes.app045_kt_currencies.data.database.dao.CurrencyDao
import com.kes.app045_kt_currencies.data.database.dao.RelativePriceDao
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.data.database.entity.RelativePriceDBModel

@Database(
    entities = [
        CurrencyDBModel::class,
        RelativePriceDBModel::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract val currencyDao: CurrencyDao
    abstract val pricesDao: RelativePriceDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "currency_database"
        const val UNDEFINED_ID: Long = 0

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = db
                return db
            }
        }
    }




}