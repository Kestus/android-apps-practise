package com.kes.app026_kt_unitconverter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        val inputView: EditText = findViewById(R.id.input)
        val outputView: TextView = findViewById(R.id.result)
        val button: Button = findViewById(R.id.submitBtn)

        button.setOnClickListener() {
            val kilos: Double = inputView.text.toString().toDouble();
            val pounds: Double = convertToPounds(kilos);
            outputView.text = pounds.toString();
        }

    }

    fun convertToPounds(kilos: Double): Double = kilos * 2.20462

}