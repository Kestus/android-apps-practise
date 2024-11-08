package com.kes.app045_kt_currencies.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.work.WorkManager
import com.kes.app045_kt_currencies.data.repository.RepositoryImpl
import com.kes.app045_kt_currencies.domain.useCases.GetCurrencyUseCase
import com.kes.app045_kt_currencies.domain.useCases.GetPriceListUseCase
import com.kes.app045_kt_currencies.domain.useCases.UpdateCurrencyUseCase

class CurrencyViewModel(
    application: Application, private val code: String
) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    val updateCurrency = UpdateCurrencyUseCase(repository)
    val getPriceList = GetPriceListUseCase(repository)
    val getCurrency = GetCurrencyUseCase(repository)

    val currentItem = getCurrency(code)
    val priceList = getPriceList(code)

    init {
        repository.loadPriceListForCurrency(code)
    }

    val isFavourite = MediatorLiveData<Boolean>().apply {
        addSource(currentItem) {
            this.value = it.favourite
        }
    }

    fun toggleFavourite() {
        currentItem.value?.let {
            it.favourite = !it.favourite
            isFavourite.value = it.favourite
            saveItemChanges()
        }
    }

    private fun saveItemChanges() {
        currentItem.value?.let {
            updateCurrency(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveItemChanges()
    }
}