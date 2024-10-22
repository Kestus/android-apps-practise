package com.kes.app045_kt_currencies.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.kes.app045_kt_currencies.data.database.AppDatabase
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.network.ApiFactory
import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.model.RelativePriceList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(application: Application) : Repository {

    private val database = AppDatabase.getInstance(application)
    private val currencyDao = database.currencyDao

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getAll(): LiveData<List<CurrencyItem>> {
        return currencyDao.getAll().map { CurrencyMapper.mapDBModelToItemList(it) }
    }

    override fun getCurrencyByID(id: Int): CurrencyItem {
        TODO("Not yet implemented")
    }

    override fun getCurrencyByCode(code: String): CurrencyItem {
        TODO("Not yet implemented")
    }

    override fun getRelativePrice(code: String): RelativePriceList {
        TODO("Not yet implemented")
    }

    override fun updatePricesForCurrency(code: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveCurrency(currency: CurrencyItem) {
        currencyDao.insertCurrency(CurrencyMapper.mapItemToDBModel(currency))
    }

    override suspend fun saveCurrency(currencyList: List<CurrencyItem>) {
        currencyDao.insertCurrencyList(CurrencyMapper.mapItemToDBModelList(currencyList))
    }

    override fun updateCurrency(currency: CurrencyItem) {
        TODO("Not yet implemented")
    }
}