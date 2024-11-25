package com.kes.app041_kt_shoppinglist.di

import android.app.Application
import com.kes.app041_kt_shoppinglist.di.module.DataModule
import com.kes.app041_kt_shoppinglist.di.module.ViewModelModule
import com.kes.app041_kt_shoppinglist.presentation.MainActivity
import com.kes.app041_kt_shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }

}