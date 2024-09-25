package com.kes.app041_kt_shoppinglist.domain

import com.kes.app041_kt_shoppinglist.data.ShopItemDAO

interface ShopListRepositoryInterface {

    suspend fun addShopItem(item: ShopItem)

    suspend fun deleteShopItem(item: ShopItem)

    suspend fun editShopItem(item: ShopItem)

    suspend fun getShopItem(id: Int): ShopItem

    fun getShopList(): List<ShopItem>
}