package com.kes.app045_kt_currencies.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kes.app045_kt_currencies.data.database.AppDatabase

@Entity(
    tableName = "prices",
    foreignKeys = [
        ForeignKey(
            entity = CurrencyDBModel::class,
            parentColumns = ["currencyId"],
            childColumns = ["baseCurrencyId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CurrencyDBModel::class,
            parentColumns = ["currencyId"],
            childColumns = ["priceCurrencyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["baseCurrencyId", "priceCurrencyId"],
    indices = [Index(value = ["priceCurrencyId"])]
)
data class RelativePriceDBModel(
    val baseCurrencyId: Long,
    val priceCurrencyId: String,
    val price: Double,
)