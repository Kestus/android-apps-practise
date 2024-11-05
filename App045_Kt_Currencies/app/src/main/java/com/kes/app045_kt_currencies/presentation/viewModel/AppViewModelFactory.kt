package com.kes.app045_kt_currencies.presentation.viewModel

import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import kotlinx.coroutines.selects.select

@Suppress("UNCHECKED_CAST")
class AppViewModelFactory(private val application: Application, private val item: CurrencyItem? = null) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(application) as T
            CurrencyViewModel::class.java -> CurrencyViewModel(application, item!!) as T

            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}