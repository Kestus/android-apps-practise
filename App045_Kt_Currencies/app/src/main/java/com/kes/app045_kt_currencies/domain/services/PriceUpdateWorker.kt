package com.kes.app045_kt_currencies.domain.services

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.kes.app045_kt_currencies.data.mapper.PriceMapper
import com.kes.app045_kt_currencies.data.network.ApiFactory
import com.kes.app045_kt_currencies.domain.Repository
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class PriceUpdateWorker(
    context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        val data = workerParameters.inputData.getString(LIST)

        // if no data (list of codes) provided, load all favourite currency codes
        val currencyCodesList = if (data != null) {
            deserialize(data)
        } else {
            repository.getAllCodes()
        }

        for (code in currencyCodesList) {
//            val response = runBlocking { apiService.getCurrency(code).execute() }
//            if (!response.isSuccessful || response.body() == null) {
//                Log.e("ERROR_API", response.message())
//                return Result.failure()
//            }

            val priceListResponse = runBlocking { apiService.getCurrency(code) }

            // Filter prices to save only those already in db
            val availableCurrencyCodes = repository.getAllCodes()
            priceListResponse.filterPriceMap(availableCurrencyCodes)
            // Select base currency from db
            val dbData =
                repository.getCurrencyWithPricesByCode(priceListResponse.baseCurrencyCode!!)
            // map response to db model
            val prices = PriceMapper.responseToDBModelList(priceListResponse)
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

        private lateinit var repository: Repository

        private val apiService by lazy {
            ApiFactory.getService()
        }

        /**
         * @param interval: Minimal period for periodic work is 15 minutes.
         */
        fun makePeriodicRequest(
            repository: Repository,
            interval: Long = 1,
            timeUnit: TimeUnit = TimeUnit.DAYS
        ): PeriodicWorkRequest {
            this.repository = repository
            return PeriodicWorkRequestBuilder<PriceUpdateWorker>(interval, timeUnit)
                .setConstraints(makeConstraints())
                .build()
        }

        fun makeRequest(
            repository: Repository,
            codeList: List<String>
        ): OneTimeWorkRequest {
            this.repository = repository
            val data = serialize(codeList)
            return OneTimeWorkRequestBuilder<PriceUpdateWorker>()
                .setInputData(data)
                .setConstraints(makeConstraints())
                .build()
        }

        fun makeRequest(
            repository: Repository,
            code: String
        ): OneTimeWorkRequest {
            return makeRequest(repository, listOf(code))
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

}