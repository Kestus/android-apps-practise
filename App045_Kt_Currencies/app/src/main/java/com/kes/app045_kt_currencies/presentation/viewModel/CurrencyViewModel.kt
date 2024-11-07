package com.kes.app045_kt_currencies.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.kes.app045_kt_currencies.data.RepositoryImpl
import com.kes.app045_kt_currencies.domain.services.PriceUpdateWorker
import com.kes.app045_kt_currencies.domain.useCases.GetCurrencyUseCase
import com.kes.app045_kt_currencies.domain.useCases.GetPriceListUseCase
import com.kes.app045_kt_currencies.domain.useCases.UpdateCurrencyUseCase

class CurrencyViewModel(
    private val application: Application, private val code: String
) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)
    private val workManager = WorkManager.getInstance(application)

    val updateCurrency = UpdateCurrencyUseCase(repository)
    val getPriceList = GetPriceListUseCase(repository)
    val getCurrency = GetCurrencyUseCase(repository)

    val currentItem = getCurrency(code)
    val priceList = getPriceList(code)

    init {
        launchPriceUpdateWork()
    }

    val isFavourite = MediatorLiveData<Boolean>().apply {
        addSource(currentItem) {
            this.value = it.favourite
        }
    }

    private fun launchPriceUpdateWork() {
        workManager.enqueueUniqueWork(
            PriceUpdateWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            PriceUpdateWorker.makeRequest(application, code)
        )
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