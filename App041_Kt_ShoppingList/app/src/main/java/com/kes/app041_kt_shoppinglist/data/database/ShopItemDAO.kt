package com.kes.app041_kt_shoppinglist.data.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ShopItemDAO {

    @Query("SELECT * FROM shopitems ORDER BY id DESC")
    fun getShopListLiveData(): LiveData<List<ShopItemDBModel>>

    @Query("SELECT * FROM shopitems")
    fun getShopListCursor(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShopItemDBModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun syncInsert(item: ShopItemDBModel)

    @Update
    fun syncUpdate(item: ShopItemDBModel): Int

    @Delete
    suspend fun delete(item: ShopItemDBModel)

    @Query("DELETE FROM shopitems WHERE id = :id")
    fun syncDeleteByID(id: Int): Int

    @Query("SELECT * FROM shopitems WHERE id LIKE :id LIMIT 1")
    suspend fun getItemById(id: Int): ShopItemDBModel

    @Query("SELECT * FROM shopitems WHERE id LIKE :id LIMIT 1")
    fun getItemByIdCursor(id: Int): Cursor

}