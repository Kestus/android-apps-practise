package com.kes.app045_kt_currencies.domain

import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.data.database.entity.CurrencyWithPrices
import com.kes.app045_kt_currencies.data.database.entity.RelativePriceDBModel

interface ServiceRepository {

    fun getCurrencyWithPricesByCode(code: String): CurrencyWithPrices

    fun getAllCodes(): List<String>

    fun insertCurrency(currency: CurrencyDBModel)

    fun insertCurrency(list: List<CurrencyDBModel>)

    fun updateCurrencyWithPrices(currency: CurrencyWithPrices)

}