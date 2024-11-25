package com.kes.app041_kt_shoppinglist.di.module

import androidx.lifecycle.ViewModel
import com.kes.app041_kt_shoppinglist.di.key.ViewModelKey
import com.kes.app041_kt_shoppinglist.presentation.viewModel.MainViewModel
import com.kes.app041_kt_shoppinglist.presentation.viewModel.ShopItemViewModel
import dagger.Binds
import dagger.Module
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

}