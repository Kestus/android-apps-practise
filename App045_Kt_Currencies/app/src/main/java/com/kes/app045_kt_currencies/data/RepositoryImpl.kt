package com.kes.app045_kt_currencies.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kes.app045_kt_currencies.data.database.AppDatabase
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryImpl(application: Application) : Repository {

    private val database = AppDatabase.getInstance(application)
    private val currencyDao = database.currencyDao

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getAll(): LiveData<List<CurrencyItem>> {
        return currencyDao.getAllLiveData().map { CurrencyMapper.mapDBModelToItemList(it) }
    }

    override fun saveCurrency(currency: CurrencyItem) {
        scope.launch {
            currencyDao.insertCurrency(CurrencyMapper.mapItemToDBModel(currency))
        }
    }

    override fun saveCurrency(currencyList: List<CurrencyItem>) {
        scope.launch {
            currencyDao.insertCurrencyList(CurrencyMapper.mapItemToDBModelList(currencyList))
        }
    }
}