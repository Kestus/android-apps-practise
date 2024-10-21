package com.kes.app045_kt_currencies.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CurrencyWithPrices (
    @Embedded
    val currency: CurrencyDBModel,

    @Relation(
        parentColumn = "currencyId",
        entityColumn = "baseCurrencyId"
    )
    val prices: List<RelativePriceDBModel>

)