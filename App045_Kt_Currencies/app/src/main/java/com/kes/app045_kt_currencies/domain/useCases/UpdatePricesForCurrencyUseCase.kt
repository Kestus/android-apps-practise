package com.kes.app045_kt_currencies.domain.useCases

import com.kes.app045_kt_currencies.domain.Repository

class UpdatePricesForCurrencyUseCase(private val repository: Repository) {
    operator fun invoke(code: String) {
        repository.updatePricesForCurrency(code)
    }
}