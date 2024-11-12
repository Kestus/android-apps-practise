package com.kes.app045_kt_currencies.domain.useCases

import androidx.lifecycle.LiveData
import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem

class GetCurrencyListUseCase(private val repository: Repository) {
    operator fun invoke(): LiveData<List<CurrencyItem>> {
        return repository.getAll()
    }
}