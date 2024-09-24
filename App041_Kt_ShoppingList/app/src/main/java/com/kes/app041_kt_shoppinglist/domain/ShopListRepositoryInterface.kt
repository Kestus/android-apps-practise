package com.kes.app041_kt_shoppinglist.domain

interface ShopListRepositoryInterface {
    fun addShopItem(item: ShopItem)

    fun deleteShopItem(item: ShopItem)

    fun editShopItem(item: ShopItem)

    fun getShopItem(id: Int): ShopItem

    fun getShopList(): List<ShopItem>
}