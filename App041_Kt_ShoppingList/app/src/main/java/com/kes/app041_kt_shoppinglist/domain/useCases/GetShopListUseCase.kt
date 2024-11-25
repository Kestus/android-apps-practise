package com.kes.app041_kt_shoppinglist.domain.useCases

import androidx.lifecycle.LiveData
import com.kes.app041_kt_shoppinglist.domain.model.ShopItem
import com.kes.app041_kt_shoppinglist.domain.Repository
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): LiveData<List<ShopItem>> = repository.getShopList()
}