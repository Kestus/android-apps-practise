package com.kes.app045_kt_currencies.data.mapper

import com.kes.app045_kt_currencies.data.database.entity.RelativePriceDBModel
import com.kes.app045_kt_currencies.data.network.model.PriceListResponse

object PriceListMapper {

    fun responseToDBModel(data: PriceListResponse): List<RelativePriceDBModel> {
        val list = mutableListOf<RelativePriceDBModel>()
        val baseCurrencyCode = data.baseCurrencyCode!!

        data.priceMap?.let {
            for ((key, value) in it.entries) {
                list.add(RelativePriceDBModel(
                    baseCurrencyCode = baseCurrencyCode,
                    priceCurrencyCode = key,
                    price = value
                ))
            }
        }
        return list
    }

}