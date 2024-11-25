package com.kes.app041_kt_shoppinglist

import android.app.Application
import com.kes.app041_kt_shoppinglist.di.DaggerApplicationComponent

class MainApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }


}