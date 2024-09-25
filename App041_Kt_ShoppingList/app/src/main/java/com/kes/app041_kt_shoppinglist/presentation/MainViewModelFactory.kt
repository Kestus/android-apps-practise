package com.kes.app041_kt_shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl

class MainViewModelFactory(
    private val repository: ShopListRepositoryImpl
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}