package com.kes.app041_kt_shoppinglist.domain

import androidx.lifecycle.LiveData
import com.kes.app041_kt_shoppinglist.domain.model.ShopItem

interface Repository {

    suspend fun addShopItem(item: ShopItem)

    suspend fun deleteShopItem(item: ShopItem)

    suspend fun editShopItem(item: ShopItem)

    suspend fun getShopItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>
}