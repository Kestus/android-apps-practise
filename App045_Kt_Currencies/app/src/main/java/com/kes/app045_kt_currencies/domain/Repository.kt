package com.kes.app045_kt_currencies.domain

import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.model.RelativePriceList

interface Repository {

    fun getAll(): List<CurrencyItem>

    fun getCurrencyByID(id: Int): CurrencyItem

    fun getCurrencyByCode(code: String): CurrencyItem

    fun getRelativePrice(code: String): RelativePriceList

}