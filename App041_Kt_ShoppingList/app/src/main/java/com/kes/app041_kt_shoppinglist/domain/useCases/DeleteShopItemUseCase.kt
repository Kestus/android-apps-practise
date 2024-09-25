package com.kes.app041_kt_shoppinglist.domain.useCases

import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class DeleteShopItemUseCase (
    private val repository: ShopListRepositoryInterface
) {
    suspend fun deleteShopItem(item: ShopItem) = repository.deleteShopItem(item)
}