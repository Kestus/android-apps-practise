package com.kes.app045_kt_currencies.domain.services

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.kes.app045_kt_currencies.data.ServiceRepositoryImpl
import com.kes.app045_kt_currencies.data.mapper.PriceListMapper
import com.kes.app045_kt_currencies.data.network.ApiFactory
import com.kes.app045_kt_currencies.domain.ServiceRepository
import java.util.concurrent.TimeUnit

class PriceUpdateWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        val currencyCodesList = deserialize(workerParameters.inputData.getString(LIST))

        for (code in currencyCodesList) {
            val response = apiService.getCurrency(code).execute()

            if (!response.isSuccessful || response.body() == null) {
                Log.e("ERROR_API", response.message())
                return Result.failure()
            }

            val priceListResponse = response.body()!!

            // Filter prices to save only those already in db
            val availableCurrencyCodes = repository.getAllCodes()
            priceListResponse.filterPriceMap(availableCurrencyCodes)

            // Select base currency from db
            val dbData = repository.getCurrencyWithPricesByCode(priceListResponse.baseCurrencyCode!!)
            // map response to db model
            val prices = PriceListMapper.responseToDBModel(priceListResponse)

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

        private var _repository: ServiceRepository? = null
        private val repository by lazy { _repository!! }

        private val apiService by lazy {
            ApiFactory.getService()
        }

        /**
         * @param interval: Minimal period for periodic work is 15 minutes.
         */
        fun makePeriodicRequest(
            application: Application,
            codeList: List<String> = listOf(),
            interval: Long = 1,
            timeUnit: TimeUnit = TimeUnit.HOURS
        ): PeriodicWorkRequest {
            _repository = ServiceRepositoryImpl(application)
            return PeriodicWorkRequestBuilder<PriceUpdateWorker>(interval, timeUnit)
                .setInputData(workDataOf(LIST to codeList))
                .setConstraints(makeConstraints())
                .build()
        }

        fun makeRequest(
            application: Application,
            codeList: List<String>
        ): OneTimeWorkRequest {
            _repository = ServiceRepositoryImpl(application)
            val data = serialize(codeList)
            return OneTimeWorkRequestBuilder<PriceUpdateWorker>()
                .setInputData(data)
                .setConstraints(makeConstraints())
                .build()
        }

        fun makeRequest(
            application: Application,
            code: String
        ) : OneTimeWorkRequest {
            return makeRequest(application, listOf(code))
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