package com.kes.app041_kt_shoppinglist.data

import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class ShopListRepositoryImpl(
    private val dao: ShopItemDAO
): ShopListRepositoryInterface {

    private val shopList = dao.getAll()

    override suspend fun addShopItem(item: ShopItem) {
        dao.insert(item)
    }

    override suspend fun deleteShopItem(item: ShopItem) {
        dao.delete(item)
    }

    override suspend fun editShopItem(item: ShopItem) {
        dao.update(item)
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        return dao.getByID(id)
    }

    override fun getShopList(): List<ShopItem> {
        return  shopList
    }

}