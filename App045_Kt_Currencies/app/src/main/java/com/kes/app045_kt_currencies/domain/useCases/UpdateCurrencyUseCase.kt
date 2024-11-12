package com.kes.app045_kt_currencies.domain.useCases

import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem

class UpdateCurrencyUseCase(private val repository: Repository) {
    operator fun invoke(currencyItem: CurrencyItem) = repository.updateCurrency(currencyItem)
}