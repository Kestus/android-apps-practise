package com.kes.app043_kt_coroutinesstart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun method() {
        val job = viewModelScope.launch(Dispatchers.Default) {
            Log.d(TAG, "Coroutine started...")
            val before = System.currentTimeMillis()
            var count = 0
            for (i in 0 until 50_000_000) {
                for (j in 0 until 100) {
                    ensureActive()
                    count++
                }
            }

            Log.d(TAG, "Finished: ${System.currentTimeMillis() - before}")
        }
        job.invokeOnCompletion {
            Log.d(TAG, "Coroutine: ${it?.message}")
        }
        viewModelScope.launch {
            delay(3000)
            job.cancel()
        }
    }


    companion object {
        private const val TAG = "TAG_MainViewModel"
    }
}