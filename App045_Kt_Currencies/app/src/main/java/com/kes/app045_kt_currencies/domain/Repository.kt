package com.kes.app045_kt_currencies.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kes.app045_kt_currencies.domain.model.CurrencyItem

interface Repository {

    fun getAll(): LiveData<List<CurrencyItem>>

    fun saveCurrency(currency: CurrencyItem)

    fun saveCurrency(currencyList: List<CurrencyItem>)

}