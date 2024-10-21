package com.kes.app045_kt_currencies.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
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
            parentColumns = ["code"],
            childColumns = ["priceCurrencyCode"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RelativePriceDBModel(
    val baseCurrencyId: Long,
    val priceCurrencyCode: String,
    val price: Double,

    @PrimaryKey(autoGenerate = true)
    val priceId: Long = AppDatabase.UNDEFINED_ID
)