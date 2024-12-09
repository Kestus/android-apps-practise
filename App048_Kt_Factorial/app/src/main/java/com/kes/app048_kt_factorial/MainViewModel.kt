package com.kes.app048_kt_factorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    private val scope = CoroutineScope(Dispatchers.Main + CoroutineName("Coroutine Scope"))

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    fun submit(input: String?) {
        _state.value = Progress

        if (input.isNullOrBlank()) {
            _state.value = Error("Input should not be empty!")
            return
        }

        val number = runCatching { input.toLong() }
            .getOrElse {
                _state.value = Error("Input should be a number!")
                return
            }

        scope.launch {
            val factorial = withContext(Dispatchers.Default) {
                factorial(number)
            }
            _state.value = Factorial(factorial)
        }
    }

//    private suspend fun asyncFactorial(num: Long): String {
//        return withContext(Dispatchers.Default) {
//            factorial(num)
//        }
//    }

//    private suspend fun asyncFactorialWithThread(num: Long): String {
//        return suspendCoroutine {
//            thread {
//                val result = factorial(num)
//                it.resumeWith(Result.success(result))
//            }
//        }
//    }

    private fun factorial(num: Long): String {
        var result = BigInteger.ONE
        for (i in 1..num) {
            result *= BigInteger.valueOf(i)
        }
        return result.toString()
    }

}