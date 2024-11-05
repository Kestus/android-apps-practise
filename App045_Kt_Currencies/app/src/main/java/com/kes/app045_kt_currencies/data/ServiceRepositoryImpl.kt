package com.kes.app045_kt_currencies.data

import android.app.Application
import android.util.Log
import com.kes.app045_kt_currencies.data.database.AppDatabase
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.data.database.entity.CurrencyWithPrices
import com.kes.app045_kt_currencies.domain.ServiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServiceRepositoryImpl(application: Application) : ServiceRepository {

    private val database = AppDatabase.getInstance(application)
    private val currencyDao = database.currencyDao
    private val pricesDao = database.pricesDao

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getCurrencyWithPricesByCode(code: String): CurrencyWithPrices {
        return currencyDao.getCurrencyWithPrices(code)
    }

    override fun getAllCodes(): List<String> {
        return currencyDao.getAllCodes()
    }

    override fun insertCurrency(currency: CurrencyDBModel) {
        scope.launch { currencyDao.insertCurrency(currency) }
    }

    override fun insertCurrency(list: List<CurrencyDBModel>) {
        scope.launch { currencyDao.insertCurrencyList(list) }
    }

    override fun updateCurrencyWithPrices(currency: CurrencyWithPrices) {
        scope.launch {
            Log.d("TAG", "updateCurrencyWithPrices: Updating...")
            pricesDao.updateCurrencyWithPrices(currency)
            Log.d("TAG", "updateCurrencyWithPrices: Updated prices for currency: ${currency.currency.code}")
            Log.d("TAG", "updateCurrencyWithPrices: ${currency.prices}")
        }
    }

}