package com.kes.app045_kt_currencies.di

import com.kes.app045_kt_currencies.di.qualifiers.CodeQualifier
import com.kes.app045_kt_currencies.presentation.fragments.CurrencyListFragment
import com.kes.app045_kt_currencies.presentation.fragments.PriceListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [ViewModelModule::class]
)
interface ActivityComponent {

    fun inject(fragment: CurrencyListFragment)

    fun inject(fragment: PriceListFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @CodeQualifier code: String? = null,
        ): ActivityComponent
    }
}