package com.kes.app049_kt_coroutineflow.currencyActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository

    val state: LiveData<CurrencyState> get() = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { CurrencyState.Content(currencyList = it) as CurrencyState }
        .onStart {
            emit(CurrencyState.Loading)
        }
        .asLiveData()

}