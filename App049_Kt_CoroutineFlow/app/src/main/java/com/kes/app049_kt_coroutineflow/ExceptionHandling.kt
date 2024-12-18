package com.kes.app049_kt_coroutineflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry

suspend fun main() {
    val flow = loadDataFlow()

    flow.map { State.Content(it) as State }
        .onStart { emit(State.Loading) }
        .retry(2) {
            println("Retrying after 1s...")
            delay(1000)
            true
        }
        .catch { emit(State.Error(it.message.toString())) }
        .collect {
            when (it) {
                is State.Content -> {
                    println("Collected: ${it.value}")
                }

                is State.Loading -> {
                    println("Loading...")
                }

                is State.Error -> {
                    println("Error: ${it.message}")
                }
            }
        }

}

fun loadDataFlow(): Flow<Int> = flow {
    repeat(5) {
        delay(300)
        emit(it)
    }
    throw RuntimeException("Something went wrong!")
}

sealed class State {
    data class Content(val value: Int) : State()
    data class Error(val message: String) : State()
    data object Loading : State()
}