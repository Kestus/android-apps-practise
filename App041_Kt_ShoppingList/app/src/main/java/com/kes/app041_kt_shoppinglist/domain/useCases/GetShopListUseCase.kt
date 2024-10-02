package com.kes.app041_kt_shoppinglist.domain.useCases

import androidx.lifecycle.LiveData
import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class GetShopListUseCase (
    private val repository: ShopListRepositoryInterface
) {
    fun getShopList(): LiveData<List<ShopItem>> = repository.getShopList()
}