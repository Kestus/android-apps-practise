package com.kes.app041_kt_shoppinglist.di.module

import android.app.Application
import com.kes.app041_kt_shoppinglist.data.RepositoryImpl
import com.kes.app041_kt_shoppinglist.data.database.AppDatabase
import com.kes.app041_kt_shoppinglist.data.database.ShopItemDAO
import com.kes.app041_kt_shoppinglist.data.mapper.ShopItemMapper
import com.kes.app041_kt_shoppinglist.di.ApplicationScope
import com.kes.app041_kt_shoppinglist.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository

    companion object {
        @ApplicationScope
        @Provides
        fun provideAppDatabase(application: Application): AppDatabase {
            return AppDatabase.getInstance(application)
        }

        @ApplicationScope
        @Provides
        fun provideShopItemDao(appDatabase: AppDatabase): ShopItemDAO {
            return appDatabase.shopItemDAO
        }

        @ApplicationScope
        @Provides
        fun provideMapper(): ShopItemMapper {
            return ShopItemMapper
        }
    }

}