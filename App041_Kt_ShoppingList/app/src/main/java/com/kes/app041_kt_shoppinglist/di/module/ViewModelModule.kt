package com.kes.app041_kt_shoppinglist.di.module

import android.app.Application
import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import com.kes.app041_kt_shoppinglist.di.key.ViewModelKey
import com.kes.app041_kt_shoppinglist.presentation.viewModel.MainViewModel
import com.kes.app041_kt_shoppinglist.presentation.viewModel.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    @Binds
    fun bindShopItemViewModel(impl: ShopItemViewModel): ViewModel

    companion object {
        @Provides
        fun provideContentProvider(application: Application): ContentResolver {
            return application.contentResolver
        }
    }

}