package com.kes.app041_kt_shoppinglist.domain.useCase

import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class EditShopItemUseCase (
    private val repository: ShopListRepositoryInterface
) {
    fun editShopItem(item: ShopItem) {
        repository.editShopItem(item)
    }
}