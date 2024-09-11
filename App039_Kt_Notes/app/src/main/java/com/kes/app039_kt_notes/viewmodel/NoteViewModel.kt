package com.kes.app039_kt_notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kes.app039_kt_notes.database.Repository
import com.kes.app039_kt_notes.models.Note
import kotlinx.coroutines.launch

class NoteViewModel(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    fun addNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun getAllNotes() = repository.getAllNotes()
    fun searchNote(query: String) = repository.searchNote(query)


}