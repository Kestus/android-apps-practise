package com.kes.app045_kt_currencies.domain.useCases

import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem

class SaveCurrencyUseCase(private val repository: Repository) {
    suspend operator fun invoke(currencyItem: CurrencyItem) = repository.saveCurrency(currencyItem)
}