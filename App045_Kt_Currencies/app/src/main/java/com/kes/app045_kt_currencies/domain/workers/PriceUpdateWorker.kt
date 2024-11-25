package com.kes.app045_kt_currencies.domain.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.kes.app045_kt_currencies.data.mapper.PriceMapper
import com.kes.app045_kt_currencies.data.network.ApiService
import com.kes.app045_kt_currencies.domain.Repository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PriceUpdateWorker(
    context: Context,
    private val workerParameters: WorkerParameters,
    private val repository: Repository,
    private val apiService: ApiService,
    private val mapper: PriceMapper,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val data = workerParameters.inputData.getString(LIST)

        // if no data (list of codes) provided, load all favourite currency codes
        val currencyCodesList = if (data != null) {
            deserialize(data)
        } else {
            repository.getAllFavCodes()
        }

        // Filter prices to save only those already in db
        val availableCurrencyCodes = repository.getAllCodes()

        for (code in currencyCodesList) {
            val priceListResponse = apiService.getCurrency(code)
            priceListResponse.filterPriceMap(availableCurrencyCodes)
            // Select base currency from db
            val dbData =
                repository.getCurrencyWithPricesByCode(priceListResponse.baseCurrencyCode!!)
            // map response to db model
            val prices = mapper.responseToDBModelList(priceListResponse)
            // update prices
            val updatedAt = priceListResponse.date!!
            dbData.submitPrices(prices, updatedAt)
            // Save to db
            repository.updateCurrencyWithPrices(dbData)
        }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "price updates work"
        const val WORK_NAME_PERIODIC = "periodic price updates work"
        private const val LIST = "list"
        private val gson = Gson()

        /**
         * @param interval: Minimal period for periodic work is 15 minutes.
         */
        fun makePeriodicRequest(
            interval: Long = 1,
            timeUnit: TimeUnit = TimeUnit.DAYS
        ): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<PriceUpdateWorker>(interval, timeUnit)
                .setConstraints(makeConstraints())
                .build()
        }

        fun makeRequest(
            codeList: List<String>
        ): OneTimeWorkRequest {
            val data = serialize(codeList)
            return OneTimeWorkRequestBuilder<PriceUpdateWorker>()
                .setInputData(data)
                .setConstraints(makeConstraints())
                .build()
        }

        fun makeRequest(
            code: String
        ): OneTimeWorkRequest {
            return makeRequest(listOf(code))
        }

        private fun makeConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
        }

        private fun serialize(list: List<String>): Data {
            val jsonString = gson.toJson(list)
            return Data.Builder()
                .putString(LIST, jsonString)
                .build()
        }

        private fun deserialize(data: String?): List<String> {
            return gson.fromJson(data, Array<String>::class.java).toList()
        }
    }

    class Factory @Inject constructor(
        private val repository: Repository,
        private val apiService: ApiService,
        private val mapper: PriceMapper,
    ): InstanceWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return PriceUpdateWorker(
                context,
                workerParameters,
                repository,
                apiService,
                mapper
            )
        }
    }

}