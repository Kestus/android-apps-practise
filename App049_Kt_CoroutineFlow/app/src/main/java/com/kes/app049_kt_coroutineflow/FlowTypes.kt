package com.kes.app049_kt_coroutineflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

private val scope = CoroutineScope(Dispatchers.IO)

suspend fun main() {
//    val flow = coldFlow()
    val flow = hotFlow()

    val job1 = scope.launch {
        flow.collect {
            println("First collector: $it")
        }
    }
    delay(5000)
    val job2 = scope.launch {
        flow.collect {
            println("Second collector: $it")
        }
    }
    job1.join()
    job2.join()
}

private fun coldFlow(): Flow<Int> = flow {
    for (i in 0 until 100) {
        println("Emitted: $i")
        emit(i)
        delay(1000)
    }
}

private fun hotFlow(): MutableSharedFlow<Int> {
    val flow = MutableSharedFlow<Int>()
    scope.launch {
        repeat(10) {
            println("Emitted: $it")
            flow.emit(it)
            delay(1000)
        }
    }
    return flow
}