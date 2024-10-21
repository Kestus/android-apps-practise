package com.kes.app045_kt_currencies.data.network.model

data class PriceListResult (
    val date: String,
    val priceList: Map<String, Double>
)