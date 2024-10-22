package com.kes.app045_kt_currencies.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kes.app045_kt_currencies.data.database.entity.CurrencyWithPrices
import com.kes.app045_kt_currencies.data.database.entity.RelativePriceDBModel

@Dao
interface RelativePriceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRelativePrice(price: RelativePriceDBModel)

    @Transaction
    @Query("SELECT * FROM currencies WHERE currencyId = :currencyId LIMIT 1")
    fun getCurrencyWithPrices(currencyId: Long): List<CurrencyWithPrices>
}