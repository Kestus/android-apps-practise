package com.kes.app045_kt_currencies.domain.UseCases

import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.CurrencyItem

class GetCurrencyListUseCase(private val repository: Repository) {
    operator fun invoke(): List<CurrencyItem> {
        return repository.getAll()
    }
}