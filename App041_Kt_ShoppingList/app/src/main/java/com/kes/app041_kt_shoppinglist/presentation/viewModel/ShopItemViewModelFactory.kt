package com.kes.app041_kt_shoppinglist.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ShopItemViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopItemViewModel::class.java)) {
            return ShopItemViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}