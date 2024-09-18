package com.kes.app040_kt_journal.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.kes.app040_kt_journal.MainActivity
import com.kes.app040_kt_journal.R
import com.kes.app040_kt_journal.adapters.JournalRecyclerAdapter
import com.kes.app040_kt_journal.databinding.ActivityJournalListBinding
import com.kes.app040_kt_journal.models.Journal

class JournalListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJournalListBinding

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var storage: StorageReference
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var collection: CollectionReference = db.collection("KtJournal")

    private lateinit var journalList: MutableList<Journal>
    private lateinit var adapter: JournalRecyclerAdapter

    private lateinit var noPostsMessage: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_journal_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Firebase Auth
        auth = Firebase.auth
        user = auth.currentUser!!

        // RecyclerView
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        noPostsMessage = binding.noPostsCard

        // Journals
        journalList = mutableListOf()
        binding.fabAdd.setOnClickListener { createJournal() }
        binding.buttonMenu.setOnClickListener {
            showMenu(it)
        }



    }

    override fun onStart() {
        super.onStart()

        collection.whereEqualTo("userID", user.uid).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    it.forEach { document ->
                        val journal = document.toObject(Journal::class.java)
                        journalList.add(journal)
                    }
                    adapter = JournalRecyclerAdapter(this, journalList)
                    binding.recyclerView.adapter = adapter
                    adapter.notifyItemRangeChanged(0, journalList.size)
                } else {
                    noPostsMessage.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Could not get journals!", Toast.LENGTH_SHORT).show()
                noPostsMessage.visibility = View.VISIBLE
                binding.noPostsCardText.text = it.toString()
                Log.w("TAG_E", it.toString())
            }



    }

    private fun createJournal() {
        if (user.isAnonymous) {
            Toast.makeText(this, "Sign-In required", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, AddJournalActivity::class.java)
        startActivity(intent)
    }

    private fun showMenu(view: View) {
        val popup = PopupMenu(this, view)
//        val inflater: MenuInflater = popup.menuInflater

        popup.apply {
            setOnMenuItemClickListener { onMenuItemClick(it) }
            inflate(R.menu.popup)
            show()
        }
    }

    private fun onMenuItemClick(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.popup_action_sign_out -> {
                signOut()
                true
            }
            else -> false
        }
    }

    private fun signOut() {
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}