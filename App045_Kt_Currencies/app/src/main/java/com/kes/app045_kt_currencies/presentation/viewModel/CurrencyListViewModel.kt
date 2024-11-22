package com.kes.app045_kt_currencies.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.useCases.GetCurrencyListUseCase
import javax.inject.Inject

@Suppress("UNUSED_PROPERTY")
class CurrencyListViewModel @Inject constructor(
    application: Application,
    private val getCurrencyList: GetCurrencyListUseCase,
) : AndroidViewModel(application) {

    private var _currencyList: LiveData<List<CurrencyItem>> = getCurrencyList()

    private var _searchInput = MutableLiveData<String?>(null)
    val searchInput: LiveData<String?> get() = _searchInput

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

    fun clearSearchInput() {
        _searchInput.value = null
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