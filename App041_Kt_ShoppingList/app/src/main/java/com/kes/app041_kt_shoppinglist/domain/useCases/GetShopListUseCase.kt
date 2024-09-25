package com.kes.app041_kt_shoppinglist.domain.useCases

import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class GetShopListUseCase (
    private val repository: ShopListRepositoryInterface
) {
    fun getShopList(): List<ShopItem> = repository.getShopList()
}