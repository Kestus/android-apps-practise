package com.kes.app045_kt_currencies.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kes.app045_kt_currencies.data.database.AppDatabase
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.mapper.PriceMapper
import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.model.RelativePriceItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryImpl(application: Application) : Repository {

    private val database = AppDatabase.getInstance(application)
    private val currencyDao = database.currencyDao
    private val priceDao = database.pricesDao

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getAll(): LiveData<List<CurrencyItem>> {
        return currencyDao.getAllLiveData().map { CurrencyMapper.mapDBModelToItemList(it) }
    }

    override fun getAllFavCodes(): List<String> {
        return currencyDao.getAllFavCodes()
    }

    override fun updateCurrency(currency: CurrencyItem) {
        scope.launch {
            currencyDao.updateCurrency(CurrencyMapper.mapItemToDBModel(currency))
        }
    }

    override fun getCurrencyByCode(code: String): LiveData<CurrencyItem> {
        return currencyDao.getCurrencyByCodeLiveData(code).map { CurrencyMapper.mapDBModelToItem(it) }
    }

    override fun getPriceList(baseCurrencyId: String): LiveData<List<RelativePriceItem>> {
        return priceDao.getPriceListLiveData(baseCurrencyId).map {
            PriceMapper.dbModelToItemList(it)
        }
    }
}