package com.kes.app040_kt_journal.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kes.app040_kt_journal.R
import com.kes.app040_kt_journal.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        binding.signUpButtonSubmit.setOnClickListener() {
            createUser()
        }

    }

    private fun createUser() {
        val email = binding.signUpInputEmail.text.toString()
        val username = binding.signUpInputUsername.text.toString()
        val pw1 = binding.signUpInputPassword.text.toString()
        val pw2 = binding.signUpInputPasswordRepeat.text.toString()
        
        if (pw1 != pw2) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pw1)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    Log.d("TAG:SignUp", "createUserWithEmail.success")
                    val user: FirebaseUser? = auth.currentUser
                    user?.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(username).build()
                    )
                    updateUI(user)
                } else {
                    Log.w("TAG:SignUp", "createUserWithEmail.failure", task.exception)
                    Toast.makeText(this, "Creation failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        // TODO("Not yet implemented")
    }

    private fun updateUI(user: FirebaseUser?) {
        // TODO("Not yet implemented")
    }
}