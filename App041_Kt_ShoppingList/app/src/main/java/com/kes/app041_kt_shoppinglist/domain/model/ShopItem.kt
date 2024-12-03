package com.kes.app041_kt_shoppinglist.domain.model

import android.content.ContentValues
import android.database.Cursor


data class ShopItem(
    val name: String,
    val count: Int,
    val id: Int = UNDEFINED_ID,
    val enabled: Boolean = true
) {

    val contentValues get() = ContentValues().apply {
        put(PARAM_ID, id)
        put(PARAM_NAME, name)
        put(PARAM_COUNT, count)
        put(PARAM_ENABLED, enabled)
    }

    companion object {
        const val UNDEFINED_ID = 0

        private const val PARAM_ID = "id"
        private const val PARAM_NAME = "name"
        private const val PARAM_COUNT = "count"
        private const val PARAM_ENABLED = "enabled"


        fun parse(cursor: Cursor): ShopItem {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PARAM_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(PARAM_NAME))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(PARAM_COUNT))
            val enabled = cursor.getInt(cursor.getColumnIndexOrThrow(PARAM_ENABLED)) > 0
            return ShopItem(name, count, id, enabled)
        }

        fun parse(values: ContentValues): ShopItem {
            val id = values.getAsInteger(PARAM_ID)
            val name = values.getAsString(PARAM_NAME)
            val count = values.getAsInteger(PARAM_COUNT)
            val enabled = values.getAsBoolean(PARAM_ENABLED)
            return ShopItem(name, count, id, enabled)
        }


    }
}