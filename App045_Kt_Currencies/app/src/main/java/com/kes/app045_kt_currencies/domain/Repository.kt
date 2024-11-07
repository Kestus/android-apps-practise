package com.kes.app045_kt_currencies.domain

import androidx.lifecycle.LiveData
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.model.RelativePriceItem

interface Repository {

    fun getAll(): LiveData<List<CurrencyItem>>

    fun getAllFavCodes(): List<String>

    fun updateCurrency(currency: CurrencyItem)

    fun getCurrencyByCode(code: String): LiveData<CurrencyItem>

    fun getPriceList(baseCurrencyId: String): LiveData<List<RelativePriceItem>>

}