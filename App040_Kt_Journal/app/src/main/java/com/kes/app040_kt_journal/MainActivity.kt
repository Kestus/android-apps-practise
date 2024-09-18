package com.kes.app040_kt_journal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kes.app040_kt_journal.activities.JournalListActivity
import com.kes.app040_kt_journal.activities.SignUpActivity
import com.kes.app040_kt_journal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        binding.apply {
            btnSubmit.setOnClickListener {
                signInWithEmailPassword(
                    signInInputEmail.text.toString().trim(),
                    signInInputPassword.text.toString().trim()
                )
            }
            btnCreate.setOnClickListener {
                redirect(SignUpActivity::class.java)
            }
        }

    }

    private fun signInWithEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    redirect(JournalListActivity::class.java)
                } else {
                    Toast.makeText(this, "Authentication failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            if(!currentUser.isAnonymous) {
                // redirect to Journal List, if already logged-in
                redirect(JournalListActivity::class.java)
            }
        }
    }

    private fun redirect(activity: Class<out AppCompatActivity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}