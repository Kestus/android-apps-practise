package com.kes.app045_kt_currencies.domain.useCases

import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import javax.inject.Inject

class UpdateCurrencyUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(currencyItem: CurrencyItem) = repository.updateCurrency(currencyItem)
}