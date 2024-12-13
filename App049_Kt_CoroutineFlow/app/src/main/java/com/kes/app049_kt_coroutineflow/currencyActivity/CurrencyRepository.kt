package com.kes.app049_kt_coroutineflow.currencyActivity

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

object CurrencyRepository {

    private val currencyNames = listOf("ASD", "ORE", "AHA", "EOO", "HUH")
    private val currencyList = mutableListOf<Currency>()

    fun getCurrencyList(): Flow<List<Currency>> = flow {
        while (true) {
            delay(3000)
            generateCurrencyList()
            emit(currencyList.toList())
            delay(3000)
        }
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