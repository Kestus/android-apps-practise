package com.kes.app039_kt_notes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kes.app039_kt_notes.database.AppDatabase
import com.kes.app039_kt_notes.database.Repository
import com.kes.app039_kt_notes.databinding.ActivityMainBinding
import com.kes.app039_kt_notes.viewmodel.NoteViewModel
import com.kes.app039_kt_notes.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: Repository

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        repository = Repository(AppDatabase.invoke(this))
        noteViewModel = NoteViewModelFactory(application, repository)
            .create(NoteViewModel::class.java)

    }
}