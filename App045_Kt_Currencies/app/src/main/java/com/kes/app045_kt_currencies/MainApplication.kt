package com.kes.app045_kt_currencies

import android.app.Application
import com.kes.app045_kt_currencies.data.di.DaggerApplicationComponent

class MainApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}