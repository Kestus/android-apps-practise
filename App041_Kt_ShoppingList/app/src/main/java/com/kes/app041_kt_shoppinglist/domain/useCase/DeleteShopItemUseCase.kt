package com.kes.app041_kt_shoppinglist.domain.useCase

import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class DeleteShopItemUseCase (
    private val repository: ShopListRepositoryInterface
) {
    fun deleteShopItem(item: ShopItem) {
        repository.deleteShopItem(item)
    }
}