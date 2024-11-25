package com.kes.app045_kt_currencies.di

import android.app.Application
import com.kes.app045_kt_currencies.MainApplication
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.network.ApiService
import com.kes.app045_kt_currencies.domain.Repository
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        BindingModule::class,
        DataModule::class,
        WorkerModule::class,
    ]
)
interface ApplicationComponent {

    fun getRepository(): Repository

    fun getApiService(): ApiService

    fun inject(application: MainApplication)

    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }

}