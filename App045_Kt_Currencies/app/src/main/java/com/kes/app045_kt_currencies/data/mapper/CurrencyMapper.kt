package com.kes.app045_kt_currencies.data.mapper

import com.kes.app045_kt_currencies.data.database.AppDatabase
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.domain.model.CurrencyItem


object CurrencyMapper {

    fun mapEntryToDBModel(entry: Map.Entry<String, String>): CurrencyDBModel {
        return CurrencyDBModel(
            code = entry.key,
            name = entry.value
        )
    }

    fun mapEntryToItem(entry: Map.Entry<String, String>): CurrencyItem {
        return CurrencyItem(
            id = AppDatabase.UNDEFINED_ID,
            code = entry.key,
            name = entry.value
        )
    }

    fun mapItemToDBModel(item: CurrencyItem): CurrencyDBModel {
        return CurrencyDBModel(
            currencyId = item.id,
            code = item.code,
            name = item.name

        )
    }

    fun mapDBModelToItem(model: CurrencyDBModel): CurrencyItem {
        return CurrencyItem(
            id = model.currencyId,
            code = model.code,
            name = model.name
        )
    }

    fun mapDBModelToItemList(list: List<CurrencyDBModel>): List<CurrencyItem> {
        return list.map { mapDBModelToItem(it) }
    }

    fun mapItemToDBModelList(list: List<CurrencyItem>): List<CurrencyDBModel> {
        return list.map { mapItemToDBModel(it) }
    }
}