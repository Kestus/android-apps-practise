package com.kes.app031_kt_fragments

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    private lateinit var btnSignIn: Button
    private lateinit var btnSignOut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSignIn = findViewById(R.id.btn_sign_in)
        btnSignOut = findViewById(R.id.btn_sign_out)

        btnSignIn.setOnClickListener() {
            val fragment: Fragment = SignInFragment()

            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_sign_in,
                fragment
            ).commit()
        }

        btnSignOut.setOnClickListener() {
            val fragment: Fragment = SignOutFragment()

            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.frame_sign_in,
                fragment
            ).commit()
        }
    }
}