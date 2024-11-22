package com.kes.app045_kt_currencies.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kes.app045_kt_currencies.di.qualifiers.CodeQualifier
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.domain.model.RelativePriceItem
import com.kes.app045_kt_currencies.domain.useCases.FetchPriceListForCurrencyUseCase
import com.kes.app045_kt_currencies.domain.useCases.GetCurrencyUseCase
import com.kes.app045_kt_currencies.domain.useCases.GetPriceListUseCase
import com.kes.app045_kt_currencies.domain.useCases.UpdateCurrencyUseCase
import javax.inject.Inject

@Suppress("UNUSED_PROPERTY")
class PriceListViewModel @Inject constructor(
    @CodeQualifier code: String?,
    private val getCurrency: GetCurrencyUseCase,
    private val getPriceList: GetPriceListUseCase,
    private val updateCurrency: UpdateCurrencyUseCase,
    private val fetchPriceListUseCase: FetchPriceListForCurrencyUseCase
) : ViewModel() {

    val currencyItem: LiveData<CurrencyItem>
    val priceList: LiveData<List<RelativePriceItem>>

    init {
        if (code == null) throw RuntimeException("PriceListViewModel requires currency code")
        currencyItem = getCurrency(code)
        priceList = getPriceList(code)
        fetchPriceListUseCase(code)
    }

    val isFavourite = MediatorLiveData<Boolean>().apply {
        addSource(currencyItem) {
            it?.let {
                this.value = it.favourite
            }
        }
    }

    fun toggleFavourite() {
        currencyItem.value?.let {
            it.favourite = !it.favourite
            isFavourite.value = it.favourite
            saveItemChanges()
        }
    }

    private fun saveItemChanges() {
        currencyItem.value?.let {
            updateCurrency(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveItemChanges()
    }
}