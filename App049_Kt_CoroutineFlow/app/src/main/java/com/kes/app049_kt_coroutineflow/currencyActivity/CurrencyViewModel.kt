package com.kes.app049_kt_coroutineflow.currencyActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository

    private val _state = MutableLiveData<CurrencyState>(CurrencyState.Initial)
    val state: LiveData<CurrencyState> get() = _state

    init {
        loadData()
    }

    private fun loadData() {
        repository.getCurrencyList()
            .onStart {
                val currentState = _state.value
                if (currentState !is CurrencyState.Content || currentState.currencyList.isEmpty()) {
                    _state.value = CurrencyState.Loading
                }
            }
            .filter { it.isNotEmpty() }
            .onEach { _state.value = CurrencyState.Content(it) }
            .launchIn(viewModelScope)
    }
}