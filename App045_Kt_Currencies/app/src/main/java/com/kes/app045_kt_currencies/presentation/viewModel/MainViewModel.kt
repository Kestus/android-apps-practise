package com.kes.app045_kt_currencies.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.kes.app045_kt_currencies.data.RepositoryImpl
import com.kes.app045_kt_currencies.data.database.AppDatabase
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.services.CurrencyUpdateWorker
import com.kes.app045_kt_currencies.domain.useCases.GetCurrencyListUseCase
import com.kes.app045_kt_currencies.domain.useCases.SaveCurrencyUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val getCurrencyList = GetCurrencyListUseCase(repository)
    private val saveCurrency = SaveCurrencyUseCase(repository)

    private val _currencyList: LiveData<List<CurrencyItem>> = getCurrencyList()

    private var _searchInput = MutableLiveData<String?>(null)

    val loading = MediatorLiveData<Boolean>().apply {
        addSource(_currencyList) {
            this.value = it.isEmpty()
        }
    }

    val filteredList = MediatorLiveData<List<CurrencyItem>>().apply {
        addSource(_currencyList) {
            this.value = it
        }
        addSource(_searchInput) {
            this.value = filterList(it)
        }
    }

    fun startUpdateCurrencyWork() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            CurrencyUpdateWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            CurrencyUpdateWorker.makeRequest(application)
        )
    }

    fun deleteAll() = viewModelScope.launch {
        val dao = AppDatabase.getInstance(application).testingDao
        dao.deleteAll()
    }

    fun setSearchInput(s: CharSequence?) {
        val input = if (s.isNullOrEmpty()) {
            null
        } else {
            s.toString()
        }
        _searchInput.value = input
    }

    private fun filterList(input: String?): List<CurrencyItem>? {
        if (input == null) {
            _currencyList.value
            return _currencyList.value
        }

        val regex = input.toRegex(RegexOption.IGNORE_CASE)
        return _currencyList.value?.filter {
            it.code.contains(regex) || it.name.contains(regex)
        }
    }

}