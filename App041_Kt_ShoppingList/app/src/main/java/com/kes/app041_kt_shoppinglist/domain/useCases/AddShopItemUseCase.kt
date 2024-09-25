package com.kes.app041_kt_shoppinglist.domain.useCases

import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class AddShopItemUseCase (
    private val repository: ShopListRepositoryInterface
) {
    suspend fun addShopItem(item: ShopItem) = repository.addShopItem(item)
}