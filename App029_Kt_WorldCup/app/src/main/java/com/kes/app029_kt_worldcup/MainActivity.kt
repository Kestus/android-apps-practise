package com.kes.app029_kt_worldcup

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.listView)

        adapter = CountryAdapter(this, getCountries())

        listView.adapter = adapter


    }

    private fun getCountries(): ArrayList<Country> {
        val result = ArrayList<Country>()
        result.add(
            Country(
                "Brazil",
                "0",
                R.drawable.brazil
            )
        )
        result.add(
            Country(
                "Mexico",
                "0",
                R.drawable.mexico
            )
        )
        result.add(
            Country(
                "Portugal",
                "0",
                R.drawable.portugal
            )
        )
        result.add(
            Country(
                "France",
                "0",
                R.drawable.france
            )
        )
        result.add(
            Country(
                "Ireland",
                "0",
                R.drawable.ireland
            )
        )


        return result
    }
}