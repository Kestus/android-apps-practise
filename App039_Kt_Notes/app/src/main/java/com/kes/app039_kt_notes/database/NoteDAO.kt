package com.kes.app039_kt_notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kes.app039_kt_notes.models.Note

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAll() : LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE title LIKE:query OR body LIKE:query ORDER BY id DESC")
    fun search(query: String) : LiveData<List<Note>>

}