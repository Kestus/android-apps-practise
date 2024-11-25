package com.kes.app045_kt_currencies.domain.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class AppWorkerFactory @Inject constructor(
    private val workerProviders: @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<InstanceWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName) {
            CurrencyUpdateWorker::class.qualifiedName -> {
                val factory = workerProviders[CurrencyUpdateWorker::class.java]?.get()
                return factory?.create(appContext, workerParameters)
            }
            PriceUpdateWorker::class.qualifiedName -> {
                val factory = workerProviders[PriceUpdateWorker::class.java]?.get()
                return factory?.create(appContext, workerParameters)
            }
            else -> null
        }
    }

}