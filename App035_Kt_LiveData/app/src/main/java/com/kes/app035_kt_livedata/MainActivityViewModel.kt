package com.kes.app035_kt_livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(startingNumber: Int = 0): ViewModel() {

    private val total = MutableLiveData<Int>()

    val sum: LiveData<Int>
        get() = total

    init {
        total.value = startingNumber
    }

    fun add(input: Int) {
        total.value = total.value?.plus(input)
    }

}