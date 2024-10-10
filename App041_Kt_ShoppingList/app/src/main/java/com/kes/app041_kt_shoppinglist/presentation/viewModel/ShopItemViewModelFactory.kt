package com.kes.app041_kt_shoppinglist.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl

@Suppress("UNCHECKED_CAST")
class ShopItemViewModelFactory(
    private val repository: ShopListRepositoryImpl
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopItemViewModel::class.java)) {
            return ShopItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}