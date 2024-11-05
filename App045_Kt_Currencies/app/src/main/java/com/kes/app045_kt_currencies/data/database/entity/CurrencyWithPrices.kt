package com.kes.app045_kt_currencies.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class CurrencyWithPrices (
    @Embedded
    val currency: CurrencyDBModel,

    @Relation(
        parentColumn = "code",
        entityColumn = "baseCurrencyCode"
    )
    var prices: List<RelativePriceDBModel>
) {
    fun submitPrices(prices: List<RelativePriceDBModel>, date: String) {
        this.prices = prices
        this.currency.pricesUpdatedAt = date
    }
}