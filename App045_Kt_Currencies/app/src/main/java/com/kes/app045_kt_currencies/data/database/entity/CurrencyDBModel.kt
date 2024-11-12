package com.kes.app045_kt_currencies.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kes.app045_kt_currencies.data.database.AppDatabase

@Entity(
    tableName = "currencies",
    indices = [Index(value = ["code"], unique = true)]
)
data class CurrencyDBModel(
    @PrimaryKey(autoGenerate = true)
    val currencyId: Long,
    val code: String,
    val name: String,
    val favourite: Boolean,
    var pricesUpdatedAt: String?,
) {
    constructor(code: String, name: String) : this(
        currencyId = AppDatabase.UNDEFINED_ID,
        code = code,
        name = name,
        favourite = false,
        pricesUpdatedAt = null,
    )
}