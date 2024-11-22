package com.kes.app045_kt_currencies.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.kes.app045_kt_currencies.data.database.AppDatabase
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.data.database.entity.CurrencyWithPrices
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.mapper.PriceMapper
import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.model.RelativePriceItem
import com.kes.app045_kt_currencies.domain.workers.CurrencyUpdateWorker
import com.kes.app045_kt_currencies.domain.workers.PriceUpdateWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    database: AppDatabase,
    private val workManager: WorkManager,
    private val mapper: CurrencyMapper
) : Repository {

    private val currencyDao = database.currencyDao
    private val pricesDao = database.pricesDao

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        startPeriodicWorkUpdateFavouriteCurrencies()
    }

    override fun getAll(): LiveData<List<CurrencyItem>> {
        return currencyDao.getAllLiveData().map { mapper.mapDBModelToItemList(it) }
    }

    override fun getAllCodes(): List<String> {
        return currencyDao.getAllCodes()
    }

    override fun getAllFavCodes(): List<String> {
        return currencyDao.getAllFavCodes()
    }

    override fun insertCurrency(currency: CurrencyDBModel) {
        scope.launch { currencyDao.insertCurrency(currency) }
    }

    override fun insertCurrency(list: List<CurrencyDBModel>) {
        scope.launch { currencyDao.insertCurrencyList(list) }
    }

    override fun updateCurrency(currency: CurrencyItem) {
        scope.launch {
            currencyDao.updateCurrency(CurrencyMapper.mapItemToDBModel(currency))
        }
    }

    override fun getCurrencyByCode(code: String): LiveData<CurrencyItem> {
        return currencyDao.getCurrencyByCodeLiveData(code)
            .map { CurrencyMapper.mapDBModelToItem(it) }
    }

    override fun getPriceList(baseCurrencyId: String): LiveData<List<RelativePriceItem>> {
        return pricesDao.getPriceListLiveData(baseCurrencyId).map {
            PriceMapper.dbModelToItemList(it)
        }
    }

    override fun getCurrencyWithPricesByCode(code: String): CurrencyWithPrices {
        return currencyDao.getCurrencyWithPrices(code)
    }

    override fun updateCurrencyWithPrices(currency: CurrencyWithPrices) {
        scope.launch {
            pricesDao.updateCurrencyWithPrices(currency)
        }
    }

    override fun fetchCurrencyList() {
        workManager.enqueueUniqueWork(
            CurrencyUpdateWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            CurrencyUpdateWorker.makeRequest()
        )
    }

    override fun fetchPriceListForCurrency(code: String) {
        workManager.enqueueUniqueWork(
            PriceUpdateWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            PriceUpdateWorker.makeRequest(code)
        )
    }

    override fun startPeriodicWorkUpdateFavouriteCurrencies() {
        workManager.enqueueUniquePeriodicWork(
            PriceUpdateWorker.WORK_NAME_PERIODIC,
            ExistingPeriodicWorkPolicy.UPDATE,
            PriceUpdateWorker.makePeriodicRequest()
        )
    }
}