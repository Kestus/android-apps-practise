package com.kes.app039_kt_notes.database

import com.kes.app039_kt_notes.models.Note

class Repository(
    private val db: AppDatabase
) {

    suspend fun insertNote(note: Note) = db.getNoteDao().insert(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().delete(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().update(note)

    fun getAllNotes() = db.getNoteDao().getAll()
    fun searchNote(query: String) = db.getNoteDao().search(query)

}