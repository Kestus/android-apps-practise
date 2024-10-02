package com.kes.app041_kt_shoppinglist.domain.useCases

import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class EditShopItemUseCase (
    private val repository: ShopListRepositoryInterface
) {
    suspend fun editShopItem(item: ShopItem) = repository.editShopItem(item)
}