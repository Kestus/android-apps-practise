package com.kes.app039_kt_notes.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kes.app039_kt_notes.database.Repository

class NoteViewModelFactory(
    private val application: Application,
    private val repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(application, repository) as T
    }

}