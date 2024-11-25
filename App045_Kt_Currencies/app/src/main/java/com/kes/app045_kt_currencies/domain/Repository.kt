package com.kes.app045_kt_currencies.domain

import androidx.lifecycle.LiveData
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.data.database.entity.CurrencyWithPrices
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.model.RelativePriceItem

interface Repository {

    fun insertCurrency(currency: CurrencyDBModel)

    fun insertCurrency(list: List<CurrencyDBModel>)

    fun getAll(): LiveData<List<CurrencyItem>>

    fun getAllCodes(): List<String>

    fun getAllFavCodes(): List<String>

    fun getCurrencyByCode(code: String): LiveData<CurrencyItem>

    fun getPriceList(baseCurrencyId: String): LiveData<List<RelativePriceItem>>

    fun getCurrencyWithPricesByCode(code: String): CurrencyWithPrices

    fun updateCurrency(currency: CurrencyItem)

    fun updateCurrencyWithPrices(currency: CurrencyWithPrices)

    fun fetchCurrencyList()

    fun fetchPriceListForCurrency(code: String)

    fun startPeriodicWorkUpdateFavouriteCurrencies()
}