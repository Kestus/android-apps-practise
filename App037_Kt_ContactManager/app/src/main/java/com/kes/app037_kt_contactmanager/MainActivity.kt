package com.kes.app037_kt_contactmanager

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kes.app037_kt_contactmanager.databinding.ActivityMainBinding
import com.kes.app037_kt_contactmanager.room.AppDatabase
import com.kes.app037_kt_contactmanager.room.Contact
import com.kes.app037_kt_contactmanager.room.Repository
import com.kes.app037_kt_contactmanager.viewmodel.ContactViewModel
import com.kes.app037_kt_contactmanager.viewmodel.ContactViewModelFactory
import com.kes.app037_kt_contactmanager.views.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Room db
        val dao = AppDatabase.getInstance(application).contactDAO
        val repository = Repository(dao)
        val factory = ContactViewModelFactory(repository)

        contactViewModel = ViewModelProvider(this, factory)[ContactViewModel::class.java]

        binding.contactViewModel = contactViewModel

        binding.lifecycleOwner = this

        initRecyclerView()


    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayContacts()
    }

    private fun displayContacts() {
        contactViewModel.contacts.observe(this, Observer {
            binding.recyclerView.adapter = RecyclerViewAdapter(it) {
                selectedItem: Contact -> listItemClicked(selectedItem)
            }
        })

    }

    private fun listItemClicked(selectedItem: Contact) {
        Toast.makeText(
            this,
            "Selected contact: ${selectedItem.name}",
            Toast.LENGTH_SHORT).show()

        contactViewModel.initUpdateOrDelete(selectedItem)
    }
}