package com.kes.app041_kt_shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kes.app041_kt_shoppinglist.domain.ShopItem

@Dao
interface ShopItemDAO {

    @Query("SELECT * FROM shopitems ORDER BY id DESC")
    fun getAll(): LiveData<List<ShopItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShopItemDBModel)

    @Delete
    suspend fun delete(item: ShopItemDBModel)

    @Query("SELECT * FROM shopitems WHERE id LIKE :id LIMIT 1")
    suspend fun getByID(id: Int): ShopItemDBModel

}