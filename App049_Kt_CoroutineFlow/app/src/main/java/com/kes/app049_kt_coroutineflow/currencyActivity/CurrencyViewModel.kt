package com.kes.app049_kt_coroutineflow.currencyActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository

    val state: LiveData<CurrencyState> = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { CurrencyState.Content(it) as CurrencyState }
        .onStart {
            Log.d(TAG, "Flow: Started")
            emit(CurrencyState.Loading)
        }
        .onEach {
            Log.d(TAG, "Flow: onEach")
        }
        .onCompletion {
            Log.d(TAG, "Flow: Finished")
        }
        .asLiveData(timeoutInMs = 5000)


    companion object {
        private const val TAG = "TAG_CurrencyViewModel"
    }
}