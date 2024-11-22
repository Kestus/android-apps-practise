package com.kes.app045_kt_currencies.data.di

import androidx.lifecycle.ViewModel
import com.kes.app045_kt_currencies.presentation.viewModel.CurrencyListViewModel
import com.kes.app045_kt_currencies.presentation.viewModel.PriceListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(CurrencyListViewModel::class)
    @Binds
    fun bindCurrencyListViewModel(impl: CurrencyListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PriceListViewModel::class)
    @Binds
    fun bindPriceListViewModel(impl: PriceListViewModel): ViewModel

}