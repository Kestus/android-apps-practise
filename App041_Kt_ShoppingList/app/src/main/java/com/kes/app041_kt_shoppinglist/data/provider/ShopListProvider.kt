package com.kes.app041_kt_shoppinglist.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.kes.app041_kt_shoppinglist.MainApplication
import com.kes.app041_kt_shoppinglist.data.database.ShopItemDAO
import com.kes.app041_kt_shoppinglist.data.mapper.ShopItemMapper
import com.kes.app041_kt_shoppinglist.domain.model.ShopItem
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

    private val component by lazy {
        (context as MainApplication).component
    }

    @Inject
    lateinit var shopItemDAO: ShopItemDAO

    @Inject
    lateinit var mapper: ShopItemMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.kes.app041_kt_shoppinglist", "items", SHOP_ITEMS_QUERY)
        addURI("com.kes.app041_kt_shoppinglist", "items/#", SHOP_ITEM_BY_ID_QUERY)

    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            SHOP_ITEMS_QUERY -> {
                shopItemDAO.getShopListCursor()
            }

            SHOP_ITEM_BY_ID_QUERY -> {
                var cursor: Cursor? = null
                uri.lastPathSegment?.toInt()?.let {
                    cursor = shopItemDAO.getItemByIdCursor(it)
                }
                return cursor
            }

            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            SHOP_ITEMS_QUERY -> {
                if (values == null) return null
                val item = ShopItem.parse(values)
                shopItemDAO.syncInsert(mapper.mapEntityToDBModel(item))
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            SHOP_ITEMS_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return shopItemDAO.syncDeleteByID(id)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        when (uriMatcher.match(uri)) {
            SHOP_ITEMS_QUERY -> {
                Log.d("TAG", "update: $values")
                values?.let {
                    val item = ShopItem.parse(it)
                    return shopItemDAO.syncUpdate(mapper.mapEntityToDBModel(item))
                }
            }
        }
        return 0
    }

    companion object {
        private const val SHOP_ITEMS_QUERY = 100
        private const val SHOP_ITEM_BY_ID_QUERY = 101
    }

}