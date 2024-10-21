package com.kes.app045_kt_currencies.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kes.app045_kt_currencies.data.database.AppDatabase

@Entity(
    tableName = "currencies",
    indices = [Index(value = ["code"], unique = true)]
)
data class CurrencyDBModel (
    val code: String,
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val currencyId: Long = AppDatabase.UNDEFINED_ID,
    val favourite: Boolean = false,
)