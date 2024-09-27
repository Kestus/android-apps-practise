package com.kes.app041_kt_shoppinglist.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShopItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val active: Boolean
)