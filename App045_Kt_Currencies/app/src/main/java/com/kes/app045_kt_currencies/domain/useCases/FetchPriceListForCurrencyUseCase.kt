package com.kes.app045_kt_currencies.domain.useCases

import com.kes.app045_kt_currencies.domain.Repository
import javax.inject.Inject

class FetchPriceListForCurrencyUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(code: String) {
        repository.fetchPriceListForCurrency(code)
    }
}