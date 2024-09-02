package com.kes.app027_kt_luckynumber

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        val inputView: EditText = findViewById(R.id.input)

        btnSubmit.setOnClickListener(){
            val name: String = inputView.text.toString()

            val i = Intent(this, LuckyNumberActivity::class.java)
            i.putExtra("name", name)
            startActivity(i)
        }
    }
}