package com.kes.app040_kt_journal.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kes.app040_kt_journal.R
import com.kes.app040_kt_journal.databinding.ActivityAddJournalBinding
import com.kes.app040_kt_journal.models.Journal
import java.util.Date

class AddJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddJournalBinding

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    // Firestore
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var journalsCollection: CollectionReference = db.collection("KtJournal")
    private lateinit var storage: StorageReference

    private lateinit var imageURI: Uri

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null && uri != Uri.EMPTY) {
            imageURI = uri
            binding.inputImagePreview.setImageURI(uri)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_journal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        storage = FirebaseStorage.getInstance().getReference()
        auth = Firebase.auth

        binding.apply {
            btnSaveJournal.setOnClickListener { saveJournal() }
            btnAddImage.setOnClickListener {
                launcher.launch("image/*")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        user = auth.currentUser!!
    }



    private fun saveJournal() {
        val title: String = binding.inputJournalTitle.text.toString().trim()
        val body: String = binding.inputJournalBody.text.toString().trim()
        if (title.isEmpty() || body.isEmpty()) {
            Toast.makeText(this, "Title and body should not be empty!", Toast.LENGTH_SHORT).show()
            return
        }
        if (Uri.EMPTY.equals(imageURI)) {
            Toast.makeText(this, "Image required!", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        // Save image in storage
        // ../journal_image/image.png
        val timestamp: Timestamp = Timestamp(Date())
        val imageReference: StorageReference = storage
            .child("journal_images")
            .child("image_${timestamp.seconds}")
        imageReference.putFile(imageURI).addOnSuccessListener {
            imageReference.downloadUrl.addOnSuccessListener {
                val imageUrl: String = it.toString()

                val journal = Journal(
                    title = title,
                    body = body,
                    imageUrl = imageUrl,
                    userID = user.uid,
                    username = user.displayName!!,
                    timeAdded = timestamp,
                )

                // Saving journal
                journalsCollection.add(journal).addOnSuccessListener {
                    binding.progressBar.visibility = View.GONE
                    val intent = Intent(this, JournalListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.addOnFailureListener {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
            Log.e("TAG", it.toString())
        }
    }

}