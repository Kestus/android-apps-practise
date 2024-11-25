package com.kes.app045_kt_currencies.domain.useCases

import androidx.lifecycle.LiveData
import com.kes.app045_kt_currencies.domain.Repository
import com.kes.app045_kt_currencies.domain.model.RelativePriceItem
import javax.inject.Inject

class GetPriceListUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(code: String): LiveData<List<RelativePriceItem>> {
        return repository.getPriceList(code)
    }
}