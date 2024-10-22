package com.kes.app045_kt_currencies.domain.services

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.kes.app045_kt_currencies.data.RepositoryImpl
import com.kes.app045_kt_currencies.data.network.ApiFactory
import com.kes.app045_kt_currencies.domain.Repository
import java.time.Duration

class PriceUpdateWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {


        TODO("Not yet implemented")
    }

    companion object {
        const val WORK_NAME = "price updates work"
        private const val LIST = "list"

//        private val repository by lazy {
//            RepositoryImpl()
//        }

        private val apiService by lazy {
            ApiFactory.getService()
        }

        /**
         * @param period: Minimal period for periodic work is 15 minutes.
         */
        fun makePeriodicRequest(
            period: Duration,
            codeList: List<String> = listOf()
        ): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<PriceUpdateWorker>(period)
                .setInputData(workDataOf(LIST to codeList))
                .setConstraints(makeConstraints())
                .build()
        }

        fun makeRequest(codeList: List<String>): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<PriceUpdateWorker>()
                .setInputData(workDataOf(LIST to codeList))
                .setConstraints(makeConstraints())
                .build()
        }

        private fun makeConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
        }
    }

}