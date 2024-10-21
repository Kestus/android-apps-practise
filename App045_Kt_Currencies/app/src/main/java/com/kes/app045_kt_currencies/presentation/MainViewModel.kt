package com.kes.app045_kt_currencies.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kes.app045_kt_currencies.data.RepositoryImpl
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.useCases.GetCurrencyListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl()
    private val getCurrencyList = GetCurrencyListUseCase(repository)
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private var _listIsEmpty: MutableLiveData<Boolean> = MutableLiveData(true)
    val listIsEmpty: LiveData<Boolean> = _listIsEmpty

    var currencyList: LiveData<List<CurrencyItem>> = getCurrencyList()


}