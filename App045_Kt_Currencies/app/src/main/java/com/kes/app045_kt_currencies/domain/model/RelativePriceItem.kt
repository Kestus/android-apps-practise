package com.kes.app045_kt_currencies.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelativePriceItem(
    val currencyCode: String,
    val price: Double
): Parcelable