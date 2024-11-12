package com.kes.app045_kt_currencies.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "prices",
    foreignKeys = [
        ForeignKey(
            entity = CurrencyDBModel::class,
            parentColumns = ["code"],
            childColumns = ["baseCurrencyCode"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CurrencyDBModel::class,
            parentColumns = ["code"],
            childColumns = ["priceCurrencyCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["baseCurrencyCode", "priceCurrencyCode"],
    indices = [Index(value = ["priceCurrencyCode"])]
)
data class RelativePriceDBModel(
    val baseCurrencyCode: String,
    val priceCurrencyCode: String,
    val price: Double,
)