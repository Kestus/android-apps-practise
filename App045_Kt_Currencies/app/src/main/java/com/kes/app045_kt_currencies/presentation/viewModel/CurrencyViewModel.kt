package com.kes.app045_kt_currencies.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.services.PriceUpdateWorker

class CurrencyViewModel(
    private val application: Application, private val item: CurrencyItem
) : AndroidViewModel(application) {

    private val workManager by lazy {
        WorkManager.getInstance(application)
    }

    fun launchPriceUpdateWork() {
        workManager.enqueueUniqueWork(
            PriceUpdateWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            PriceUpdateWorker.makeRequest(application, item.code)
        )
    }
}