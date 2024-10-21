package com.kes.app045_kt_currencies.data.network.model

import android.util.Log
import com.kes.app045_kt_currencies.domain.model.CurrencyItem

data class CurrenciesResult(
    val currencyMap: Map<String, String>
) {

    fun toCurrencyItemList(): List<CurrencyItem> {
        val list: List<CurrencyItem> = mutableListOf()

        for (currency in currencyMap) {
            Log.d("TAG", "toCurrencyItemList: $currency")
        }

        return list
    }


}