package com.kes.app045_kt_currencies.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.data.database.entity.CurrencyWithPrices

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrency(currency: CurrencyDBModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrencyList(list: List<CurrencyDBModel>)

    @Update
    fun updateCurrency(currency: CurrencyDBModel)

    @Delete
    fun deleteCurrency(currency: CurrencyDBModel)

    @Query("SELECT * FROM currencies ORDER BY code")
    fun getAll(): List<CurrencyDBModel>

    @Query("SELECT * FROM currencies ORDER BY favourite DESC, code")
    fun getAllLiveData(): LiveData<List<CurrencyDBModel>>

    @Query("SELECT code FROM currencies")
    fun getAllCodes(): List<String>

    @Query("SELECT * FROM currencies WHERE favourite = 1 ORDER BY code")
    fun getAllFavourite(): List<CurrencyDBModel>

    @Query("SELECT * FROM currencies WHERE currencyId = :currencyId LIMIT 1")
    fun getCurrencyById(currencyId: Long): CurrencyDBModel

    @Query("SELECT * FROM currencies WHERE code = :code LIMIT 1")
    fun getCurrencyByCodeLiveData(code: String): LiveData<CurrencyDBModel>

    @Query("SELECT * FROM currencies WHERE code = :currencyCode LIMIT 1")
    fun getCurrencyByCode(currencyCode: String): CurrencyDBModel

    @Query("SELECT * FROM currencies WHERE code = :currencyCode LIMIT 1")
    fun getCurrencyWithPrices(currencyCode: String): CurrencyWithPrices

    @Query("SELECT code FROM currencies WHERE favourite = 1 ORDER BY code")
    fun getAllFavCodes(): List<String>
}