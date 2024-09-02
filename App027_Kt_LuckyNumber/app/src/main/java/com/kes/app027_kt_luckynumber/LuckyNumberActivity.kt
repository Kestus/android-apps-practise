package com.kes.app027_kt_luckynumber

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class LuckyNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lucky_number)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val resultView: TextView = findViewById(R.id.resultView)
        val btnShare: Button = findViewById(R.id.btnShare)

        val name = getName()
        val number = generateNumber()
        resultView.text = number.toString()

        btnShare.setOnClickListener() {
            shareResult(name, number)
        }

    }


    private fun getName(): String {
        val bundle: Bundle? = intent.extras
        val name: String = bundle?.getString("name").toString()
        return name
    }

    private fun generateNumber(): Int = Random.nextInt(1000)

    private fun shareResult(name: String, number: Int) {
        val i = Intent(Intent.ACTION_SEND)
        i.setType("text/plain")
        i.putExtra(Intent.EXTRA_SUBJECT, "$name is lucky!")
        i.putExtra(Intent.EXTRA_TEXT, "Their lucky number is: $number")
        startActivity(i)
    }


}