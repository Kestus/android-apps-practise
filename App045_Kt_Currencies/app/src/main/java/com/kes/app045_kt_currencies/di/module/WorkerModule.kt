package com.kes.app045_kt_currencies.di.module

import com.kes.app045_kt_currencies.di.key.WorkerKey
import com.kes.app045_kt_currencies.domain.workers.InstanceWorkerFactory
import com.kes.app045_kt_currencies.domain.workers.CurrencyUpdateWorker
import com.kes.app045_kt_currencies.domain.workers.PriceUpdateWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(CurrencyUpdateWorker::class)
    fun bindCurrencyUpdateWorkerFactory(worker: CurrencyUpdateWorker.Factory): InstanceWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(PriceUpdateWorker::class)
    fun bindPriceUpdateWorkerFactory(worker: PriceUpdateWorker.Factory): InstanceWorkerFactory

}