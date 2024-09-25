package com.kes.app041_kt_shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kes.app041_kt_shoppinglist.domain.ShopItem

@Database(
    entities = [ShopItem::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    // Create DB Instance
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_db"
                    ).build()
                }

                return INSTANCE as AppDatabase
            }
        }
    }
}