package com.kes.app043_kt_coroutinesstart

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.kes.app043_kt_coroutinesstart.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnDownload.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        binding.apply {
            progressBar.isVisible = true
            btnDownload.isEnabled = false

            loadCity { city ->
                tvCity.text = city

                loadTemp(city) { temp ->
                    tvTemp.text = temp.toString()
                    progressBar.isVisible = false
                    btnDownload.isEnabled = true
                }
            }
        }
    }

    private fun loadCity(callback: (String)->Unit) {
        thread {
            Thread.sleep(2000)
            handler.post {
                callback.invoke("Moscow")
            }
        }
    }

    private fun loadTemp(city: String, callback: (Int)->Unit) {
        thread {
            handler.post {
                Toast.makeText(this, "Loading temp for city: $city", Toast.LENGTH_SHORT).show()
            }
            Thread.sleep(2000)
            handler.post {
                callback.invoke(19)
            }

        }
    }
}