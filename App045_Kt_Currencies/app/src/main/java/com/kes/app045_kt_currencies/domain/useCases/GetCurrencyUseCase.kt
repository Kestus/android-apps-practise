package com.kes.app045_kt_currencies.domain.useCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem

class GetCurrencyUseCase(private val repository: Repository) {
    operator fun invoke(code: String): LiveData<CurrencyItem> {
        return repository.getCurrencyByCode(code)
    }
}