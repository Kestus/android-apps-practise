package com.kes.app041_kt_shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.domain.ShopListRepositoryInterface

class ShopListRepositoryImpl(
    application: Application
): ShopListRepositoryInterface {

    private val dao = AppDatabase.getInstance(application).shopItemDAO
    private val mapper = ShopItemMapper()

    override suspend fun addShopItem(item: ShopItem) {
        dao.insert(mapper.mapEntityToDBModel(item))
    }

    override suspend fun deleteShopItem(item: ShopItem) {
        dao.delete(mapper.mapEntityToDBModel(item))
    }

    override suspend fun editShopItem(item: ShopItem) {
        dao.insert(mapper.mapEntityToDBModel(item))
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        val item = dao.getByID(id)
        return mapper.mapDBModelToEntity(item)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = dao.getAll().map {
        mapper.mapListDBModelToListEntity(it)
    }

}