package com.kes.app049_kt_coroutineflow.currencyActivity

sealed class CurrencyState {
    data object Initial : CurrencyState()
    data object Loading : CurrencyState()
    data class Content(val currencyList: List<Currency>) : CurrencyState()
}
