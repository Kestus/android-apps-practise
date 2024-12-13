package com.kes.app049_kt_coroutineflow.currencyActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository

    private val _state = MutableLiveData<CurrencyState>(CurrencyState.Initial)
    val state: LiveData<CurrencyState> get() = _state

    private var job: Job? = null
    private var isResumed = false

    fun loadData() {
        isResumed = true
        if (job != null) return
        job = repository.getCurrencyList()
            .onStart {
                Log.d(TAG, "Flow: Started")
                _state.value = CurrencyState.Loading
            }
            .filter { it.isNotEmpty() }
            .onEach {
                Log.d(TAG, "Flow: onEach")
                _state.value = CurrencyState.Content(it)
            }
            .onCompletion {
                Log.d(TAG, "Flow: Finished")
            }
            .launchIn(viewModelScope)
    }

    fun stopLoading() {
        viewModelScope.launch {
            delay(5000)
            if (!isResumed) {
                job?.cancel()
                job = null
            } else {
                isResumed = false
            }
        }
    }

    companion object {
        private const val TAG = "TAG_CurrencyViewModel"
    }
}