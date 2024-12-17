package com.kes.app049_kt_coroutineflow.currencyActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository

    val state: Flow<CurrencyState> = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { CurrencyState.Content(it) as CurrencyState }
        .onStart { emit(CurrencyState.Loading) }


    fun refreshList() {
        viewModelScope.launch {
            repository.refreshList()
        }
    }
}