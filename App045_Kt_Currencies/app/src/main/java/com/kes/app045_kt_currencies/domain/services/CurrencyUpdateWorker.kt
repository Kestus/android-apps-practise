package com.kes.app045_kt_currencies.domain.services

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kes.app045_kt_currencies.data.RepositoryImpl
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.network.ApiFactory
import com.kes.app045_kt_currencies.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyUpdateWorker(
    context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        var result = Result.success()

        apiService.getLatest().enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(
                p0: Call<Map<String, String>>,
                p1: Response<Map<String, String>>
            ) {
                val data = p1.body() ?: return onFailure(p0, Throwable("api call returned empty"))
                // save data to db
                data.filter { it.key.length == 3 }    // filter-out non three-letter codes
                    .filter { it.value.isNotEmpty() } // filter-out empty names
                    .map { CurrencyMapper.mapEntryToItem(it) }
                    .apply {
                        ioScope.launch { repository.saveCurrency(this@apply) }
                    }

            }

            override fun onFailure(p0: Call<Map<String, String>>, p1: Throwable) {
                Log.e("ERROR_API", p1.message.toString())
                result = Result.failure()
            }
        })
        return result
    }

    companion object {
        const val WORK_NAME = "currency updates work"
        private val ioScope = CoroutineScope(Dispatchers.IO)

        private var _repository: Repository? = null
        private val repository by lazy {
            _repository!!
        }

        private val apiService by lazy {
            ApiFactory.getService()
        }

        fun makeRequest(application: Application): OneTimeWorkRequest {
            _repository = RepositoryImpl(application)
            return OneTimeWorkRequestBuilder<CurrencyUpdateWorker>()
                .build()
        }
    }
}