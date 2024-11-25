package com.kes.app041_kt_shoppinglist.domain.model


data class ShopItem(
    val name: String,
    val count: Int,
    val id: Int = UNDEFINED_ID,
    val enabled: Boolean = true
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}