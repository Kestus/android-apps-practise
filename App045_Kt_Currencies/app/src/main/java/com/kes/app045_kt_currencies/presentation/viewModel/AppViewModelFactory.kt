package com.kes.app045_kt_currencies.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class AppViewModelFactory(private val application: Application, private val code: String? = null) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(application) as T
            CurrencyViewModel::class.java -> CurrencyViewModel(application, code!!) as T

            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}