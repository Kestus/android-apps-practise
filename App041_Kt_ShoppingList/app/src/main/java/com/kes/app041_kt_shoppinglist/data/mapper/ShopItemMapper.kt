package com.kes.app041_kt_shoppinglist.data.mapper

import com.kes.app041_kt_shoppinglist.data.database.ShopItemDBModel
import com.kes.app041_kt_shoppinglist.domain.model.ShopItem

object ShopItemMapper {

    fun mapEntityToDBModel(item: ShopItem): ShopItemDBModel {
        return ShopItemDBModel(
            id = item.id,
            name = item.name,
            count = item.count,
            enabled = item.enabled
        )
    }

    fun mapDBModelToEntity(item: ShopItemDBModel): ShopItem {
        return ShopItem(
            id = item.id,
            name = item.name,
            count = item.count,
            enabled = item.enabled
        )
    }

    fun mapListDBModelToListEntity(list: List<ShopItemDBModel>): List<ShopItem> = list.map {
        mapDBModelToEntity(it)
    }

    fun mapListEntityToListDBModel(list: List<ShopItem>): List<ShopItemDBModel> = list.map {
        mapEntityToDBModel(it)
    }
}