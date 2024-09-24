package com.kes.app041_kt_shoppinglist.domain.useCase

import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class GetShopListUseCase (
    private val repository: ShopListRepositoryInterface
) {
    fun getShopList(): List<ShopItem> {
        return repository.getShopList()
    }
}