package com.kes.app045_kt_currencies.di.module

import android.app.Application
import androidx.work.WorkManager
import com.kes.app045_kt_currencies.data.database.AppDatabase
import com.kes.app045_kt_currencies.data.database.dao.CurrencyDao
import com.kes.app045_kt_currencies.data.database.dao.PriceDao
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import com.kes.app045_kt_currencies.data.mapper.PriceMapper
import com.kes.app045_kt_currencies.data.network.ApiFactory
import com.kes.app045_kt_currencies.data.network.ApiService
import com.kes.app045_kt_currencies.data.repository.RepositoryImpl
import com.kes.app045_kt_currencies.di.ApplicationScope
import com.kes.app045_kt_currencies.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {
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
        fun provideCurrencyMapper(): CurrencyMapper {
            return CurrencyMapper
        }

        @ApplicationScope
        @Provides
        fun providePriceMapper(): PriceMapper {
            return PriceMapper
        }

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.getService()
        }

        @ApplicationScope
        @Provides
        fun provideCurrencyDao(database: AppDatabase): CurrencyDao {
            return database.currencyDao
        }

        @ApplicationScope
        @Provides
        fun providePriceDao(database: AppDatabase): PriceDao {
            return database.priceDao
        }
    }

}