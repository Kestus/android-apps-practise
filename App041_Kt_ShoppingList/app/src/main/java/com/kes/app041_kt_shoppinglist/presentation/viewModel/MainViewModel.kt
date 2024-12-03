package com.kes.app041_kt_shoppinglist.presentation.viewModel

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kes.app041_kt_shoppinglist.domain.model.ShopItem
import com.kes.app041_kt_shoppinglist.domain.useCases.DeleteShopItemUseCase
import com.kes.app041_kt_shoppinglist.domain.useCases.EditShopItemUseCase
import com.kes.app041_kt_shoppinglist.domain.useCases.GetShopListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val contentResolver: ContentResolver,
) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    var shopList: LiveData<List<ShopItem>> = getShopListUseCase()

    fun deleteShopItem(item: ShopItem) = viewModelScope.launch {
        deleteShopItemUseCase(item)
    }

    fun deleteShopItemProvider(item: ShopItem) = viewModelScope.launch {
        ioScope.launch {
            contentResolver.delete(
                Uri.parse("content://com.kes.app041_kt_shoppinglist/items"),
                null,
                arrayOf(item.id.toString())
            )
        }
    }

    fun editShopItem(item: ShopItem) = viewModelScope.launch {
        editShopItemUseCase(item)
    }

    fun changeActiveState(item: ShopItem) = viewModelScope.launch {
        val newItem = item.copy(enabled = !item.enabled)
        editShopItemUseCase(newItem)
    }

}