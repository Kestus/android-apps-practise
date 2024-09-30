package com.kes.app041_kt_shoppinglist.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShopItem(
    val name: String,
    val count: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNDEFINED_ID,
    val active: Boolean = true
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}