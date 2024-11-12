package com.kes.app045_kt_currencies.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyItem(
    val id: Long,
    val code: String,
    val name: String,
    var pricesUpdatedAt: String?,
    var favourite: Boolean
) : Parcelable