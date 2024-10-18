package com.kes.app045_kt_currencies.domain.model

data class RelativePriceList(
    val baseCurrency: CurrencyItem,
    val priceList: Map<CurrencyItem, Double>
)