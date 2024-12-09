package com.kes.app043_kt_coroutinesstart

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val parentJob = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, "Exception: $throwable")
    }
    private val scope = CoroutineScope(Dispatchers.Main + parentJob + exceptionHandler)

    fun method() {
        val childJob1 = scope.launch {
            delay(3000)
            Log.d(TAG, "method: childJob1 - Finished")
        }
        val childJob2 = scope.launch {
            delay(1000)
            Log.d(TAG, "method: childJob2 - Finished")
        }

        val childJob3 = scope.async {
            delay(2000)
            error()
            Log.d(TAG, "method: childJob2 - Finished")
        }
    }

    private fun error() {
        throw RuntimeException()
    }

    companion object {
        private const val TAG = "TAG_MainViewModel"
    }
}