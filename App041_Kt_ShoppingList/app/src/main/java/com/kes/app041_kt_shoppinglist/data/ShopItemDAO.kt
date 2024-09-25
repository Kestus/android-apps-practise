package com.kes.app041_kt_shoppinglist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kes.app041_kt_shoppinglist.domain.ShopItem

@Dao
interface ShopItemDAO {
    @Insert
    suspend fun insert(item: ShopItem): Int

    @Delete
    suspend fun delete(item: ShopItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: ShopItem)

    @Query("SELECT * FROM shopitem WHERE id LIKE :id LIMIT 1")
    suspend fun getByID(id: Int): ShopItem

    @Query("SELECT * FROM shopitem")
    fun getAll(): List<ShopItem>
}