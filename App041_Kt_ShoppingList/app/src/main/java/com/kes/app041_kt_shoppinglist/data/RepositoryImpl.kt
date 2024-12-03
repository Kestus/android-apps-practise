package com.kes.app041_kt_shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kes.app041_kt_shoppinglist.data.database.ShopItemDAO
import com.kes.app041_kt_shoppinglist.data.mapper.ShopItemMapper
import com.kes.app041_kt_shoppinglist.domain.model.ShopItem
import com.kes.app041_kt_shoppinglist.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: ShopItemDAO,
    private val mapper: ShopItemMapper
): Repository {

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
        val item = dao.getItemById(id)
        return mapper.mapDBModelToEntity(item)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = dao.getShopListLiveData().map {
        mapper.mapListDBModelToListEntity(it)
    }

}