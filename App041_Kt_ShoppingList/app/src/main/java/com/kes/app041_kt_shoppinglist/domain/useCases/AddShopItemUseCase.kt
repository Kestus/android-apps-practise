package com.kes.app041_kt_shoppinglist.domain.useCases

import com.kes.app041_kt_shoppinglist.domain.model.ShopItem
import com.kes.app041_kt_shoppinglist.domain.Repository
import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(item: ShopItem) = repository.addShopItem(item)
}