package com.kes.app041_kt_shoppinglist.domain.useCase

import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class GetShopItemUseCase (
    private val repository: ShopListRepositoryInterface
) {
    fun getShopItem(id: Int): ShopItem {
        return repository.getShopItem(id)
    }
}