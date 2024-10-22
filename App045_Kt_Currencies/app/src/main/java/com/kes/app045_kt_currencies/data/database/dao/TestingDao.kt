package com.kes.app045_kt_currencies.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TestingDao {

    @Query("DELETE FROM currencies")
    suspend fun deleteAllCurrencies()

    @Query("DELETE FROM prices")
    suspend fun deleteAllPrices()

    @Transaction
    suspend fun deleteAll() {
        deleteAllPrices()
        deleteAllCurrencies()
    }

}