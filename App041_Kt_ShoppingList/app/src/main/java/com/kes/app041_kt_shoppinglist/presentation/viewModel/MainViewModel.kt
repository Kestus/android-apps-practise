package com.kes.app041_kt_shoppinglist.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl
import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.useCases.DeleteShopItemUseCase
import com.kes.app041_kt_shoppinglist.domain.useCases.EditShopItemUseCase
import com.kes.app041_kt_shoppinglist.domain.useCases.GetShopListUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    var shopList: LiveData<List<ShopItem>> = getShopListUseCase.getShopList()

    fun deleteShopItem(item: ShopItem) = viewModelScope.launch {
        deleteShopItemUseCase.deleteShopItem(item)
    }

    fun editShopItem(item: ShopItem) = viewModelScope.launch {
        editShopItemUseCase.editShopItem(item)
    }

    fun changeActiveState(item: ShopItem) = viewModelScope.launch {
        val newItem = item.copy(enabled = !item.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }

}