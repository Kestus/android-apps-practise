package com.kes.app045_kt_currencies.domain.model

import android.os.Parcelable
import com.kes.app045_kt_currencies.data.database.entity.CurrencyDBModel
import com.kes.app045_kt_currencies.data.mapper.CurrencyMapper
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyItem(
    val id: Long,
    val code: String,
    val name: String,
    var pricesUpdatedAt: String? = null,
    var priceList: List<RelativePriceItem>? = null,
    var favourite: Boolean = false
) : Parcelable {
    fun toDBModel(): CurrencyDBModel = CurrencyMapper.mapItemToDBModel(this)
}