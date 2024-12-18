package com.kes.app049_kt_coroutineflow.currencyActivity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.random.Random

object CurrencyRepository {

    private val currencyNames = listOf("ASD", "ORE", "AHA", "EOO", "HUH")
    private val currencyList = mutableListOf<Currency>()

    private val refreshEvents = MutableSharedFlow<Unit>()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    val currencyListFlow: Flow<List<Currency>> = flow {
        delay(3000)
        generateCurrencyList()
        emit(currencyList.toList())
        refreshEvents.collect {
            delay(3000)
            generateCurrencyList()
            emit(currencyList.toList())
        }
    }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = currencyList.toList(),
        )

    suspend fun refreshList() {
        refreshEvents.emit(Unit)
    }

    private fun generateCurrencyList() {
        val prices = buildList {
            repeat(currencyNames.size) {
                add(Random.nextInt(500, 1500))
            }
        }
        val data = buildList {
            for ((index, name) in currencyNames.withIndex()) {
                val price = prices[index]
                val currency = Currency(name, price)
                add(currency)
            }
        }
        currencyList.clear()
        currencyList.addAll(data)
    }
}