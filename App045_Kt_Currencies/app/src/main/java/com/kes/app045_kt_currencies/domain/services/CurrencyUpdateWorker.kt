package com.kes.app045_kt_currencies.domain.services

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kes.app045_kt_currencies.data.ServiceRepositoryImpl
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.network.ApiFactory
import com.kes.app045_kt_currencies.domain.ServiceRepository

class CurrencyUpdateWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        val response = apiService.getLatest().execute()

        if (!response.isSuccessful || response.body() == null) {
            Log.e("ERROR_API", response.message())
            return Result.failure()
        }

        val data = response.body()!!

        // filter and save data to db
        data.asSequence().filter { it.key.length == 3 }    // filter-out non three-letter codes
            .filter { it.value.isNotEmpty() } // filter-out empty names
            .filter { !it.value.contains("(coin)".toRegex(RegexOption.IGNORE_CASE)) } // filter-out "coins"
            .filter { !it.value.contains("(token)".toRegex(RegexOption.IGNORE_CASE)) } // filter-out "tokens"
            .map { CurrencyMapper.mapEntryToDBModel(it) }.toList()
            .apply {
                repository.insertCurrency(this@apply)
            }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "currency updates work"

        private var _repository: ServiceRepository? = null
        private val repository by lazy {
            _repository!!
        }

        private val apiService by lazy {
            ApiFactory.getService()
        }

        fun makeRequest(application: Application): OneTimeWorkRequest {
            _repository = ServiceRepositoryImpl(application)
            return OneTimeWorkRequestBuilder<CurrencyUpdateWorker>()
                .build()
        }
    }
}