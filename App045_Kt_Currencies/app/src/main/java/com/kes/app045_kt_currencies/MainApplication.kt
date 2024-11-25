package com.kes.app045_kt_currencies

import android.app.Application
import androidx.work.Configuration
import com.kes.app045_kt_currencies.di.DaggerApplicationComponent
import com.kes.app045_kt_currencies.domain.workers.AppWorkerFactory
import javax.inject.Inject

class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: AppWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }



    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory).build()
}