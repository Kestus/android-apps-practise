package com.kes.app045_kt_currencies.data.di

import android.app.Application
import androidx.work.WorkManager
import com.kes.app045_kt_currencies.data.database.AppDatabase
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.network.ApiFactory
import com.kes.app045_kt_currencies.data.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideWorkManager(application: Application): WorkManager {
        return WorkManager.getInstance(application)
    }

    @ApplicationScope
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @ApplicationScope
    @Provides
    fun provideMapper(): CurrencyMapper {
        return CurrencyMapper
    }

    @ApplicationScope
    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.getService()
    }

}