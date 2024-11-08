package com.kes.app045_kt_currencies.data.mapper

import com.kes.app045_kt_currencies.data.database.entity.RelativePriceDBModel
import com.kes.app045_kt_currencies.data.network.model.PriceListDto
import com.kes.app045_kt_currencies.domain.model.RelativePriceItem

object PriceMapper {

    fun responseToDBModelList(data: PriceListDto): List<RelativePriceDBModel> {
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

    fun dbModelToItemList(data: List<RelativePriceDBModel>): List<RelativePriceItem> {
        return data.map { dbModelToItem(it) }
    }

    fun dbModelToItem(data: RelativePriceDBModel): RelativePriceItem {
        return RelativePriceItem(data.priceCurrencyCode, data.price)
    }
}