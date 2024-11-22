package com.kes.app045_kt_currencies.domain.useCases

import com.kes.app045_kt_currencies.domain.Repository
import javax.inject.Inject

class FetchCurrencyListUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() {
        repository.fetchCurrencyList()
    }
}