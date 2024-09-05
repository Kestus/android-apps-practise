package com.kes.app036_kt_counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private val mutableCounter = MutableLiveData<Int>()

    val counter: LiveData<Int>
        get() = mutableCounter

    init {
        mutableCounter.value = 0
    }

    fun updateCounter() {
        mutableCounter.value = mutableCounter.value?.plus(1)
    }

}