package com.kes.app045_kt_currencies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCurrency(currency: CurrencyDBModel)

    @Update
    fun updateCurrency(currency: CurrencyDBModel)



}