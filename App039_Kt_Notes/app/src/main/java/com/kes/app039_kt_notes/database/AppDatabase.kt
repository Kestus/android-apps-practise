package com.kes.app039_kt_notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kes.app039_kt_notes.models.Note

@Database(entities = [
    Note::class
], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
            synchronized(LOCK) {
                instance ?:
                createDatabase(context).also {
                    instance = it
                }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_db"
            ).build()
    }

}