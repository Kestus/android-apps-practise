package com.kes.app049_kt_coroutineflow.currencyActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository

    private val loadingFlow = MutableSharedFlow<CurrencyState>()

    val state: Flow<CurrencyState> = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { CurrencyState.Content(it) as CurrencyState }
        .onStart { emit(CurrencyState.Loading) }
        .mergeWith(loadingFlow)

    fun refreshList() {
        viewModelScope.launch {
            loadingFlow.emit(CurrencyState.Loading)
            repository.refreshList()
        }
    }

    private fun <T> Flow<T>.mergeWith(flow: Flow<T>): Flow<T> {
        return merge(this, flow)
    }
}