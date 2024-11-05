package com.kes.app045_kt_currencies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.data.database.entity.CurrencyWithPrices
import com.kes.app045_kt_currencies.data.database.entity.RelativePriceDBModel

@Dao
interface RelativePriceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRelativePrice(price: RelativePriceDBModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRelativePriceList(priceList: List<RelativePriceDBModel>)

    @Update
    fun updateCurrency(currency: CurrencyDBModel)

    @Transaction
    fun updateCurrencyWithPrices(data: CurrencyWithPrices) {
        updateCurrency(data.currency)
        insertRelativePriceList(data.prices)
    }

    @Query("SELECT * FROM currencies WHERE currencyId = :currencyId LIMIT 1")
    fun getCurrencyWithPrices(currencyId: Long): CurrencyWithPrices

    @Query("SELECT * " +
            "FROM prices " +
            "WHERE baseCurrencyCode = :baseCode " +
            "AND priceCurrencyCode = :priceCode " +
            "LIMIT 1")
    fun getRelativePrice(baseCode: String, priceCode: String): RelativePriceDBModel?
}