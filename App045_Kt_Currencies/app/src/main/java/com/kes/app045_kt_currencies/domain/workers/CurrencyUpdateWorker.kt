package com.kes.app045_kt_currencies.domain.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.network.ApiService
import com.kes.app045_kt_currencies.domain.Repository
import javax.inject.Inject

class CurrencyUpdateWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val repository: Repository,
    private val apiService: ApiService,
    private val mapper: CurrencyMapper,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val data = apiService.getLatest()

        // filter and save data to db
        data.asSequence().filter { it.key.length == 3 }    // filter-out non three-letter codes
            .filter { it.value.isNotEmpty() } // filter-out empty names
            .filter { !it.value.contains("(coin)".toRegex(RegexOption.IGNORE_CASE)) } // filter-out "coins"
            .filter { !it.value.contains("(token)".toRegex(RegexOption.IGNORE_CASE)) } // filter-out "tokens"
            .map { mapper.mapEntryToDBModel(it) }.toList()
            .apply {
                repository.insertCurrency(this@apply)
            }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "currency updates work"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<CurrencyUpdateWorker>()
                .build()
        }
    }

    class Factory @Inject constructor(
        private val repository: Repository,
        private val apiService: ApiService,
        private val mapper: CurrencyMapper,
    ) : InstanceWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return CurrencyUpdateWorker(
                context,
                workerParameters,
                repository,
                apiService,
                mapper
            )
        }
    }
}