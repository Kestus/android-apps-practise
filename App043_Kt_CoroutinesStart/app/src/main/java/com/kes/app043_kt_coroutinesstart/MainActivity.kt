package com.kes.app043_kt_coroutinesstart

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.kes.app043_kt_coroutinesstart.databinding.ActivityMainBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

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
            binding.apply {
                progressBar.isVisible = true
                btnDownload.isEnabled = false
                val deferredCity = lifecycleScope.async { loadCity() }
                val deferredTemp = lifecycleScope.async { loadTemp() }

                lifecycleScope.launch {
                    val city = deferredCity.await()
                    tvCity.text = city
                    val temp = deferredTemp.await()
                    tvTemp.text = temp.toString()
                    progressBar.isVisible = false
                    btnDownload.isEnabled = true
                    Toast.makeText(
                        this@MainActivity,
                        "City: $city, Temperature: $temp",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


//            loadDataWithoutCoroutines()
        }
    }

    private suspend fun loadData() {
        binding.apply {
            progressBar.isVisible = true
            btnDownload.isEnabled = false

            val city = loadCity()
            tvCity.text = city

            val temp = loadTemp()
            tvTemp.text = temp.toString()

            progressBar.isVisible = false
            btnDownload.isEnabled = true
        }
    }

    private suspend fun loadCity(): String {
        delay(2000)
        return "Moscow"
    }

    private suspend fun loadTemp(): Int {
        delay(3000)
        return Random.nextInt(-20, 40)
    }

    // State machine
    private fun loadDataWithoutCoroutines(step: Int = 0, obj: Any? = null) {
        when (step) {
            0 -> {
                Log.d("TAG", "Loading started...")
                binding.progressBar.isVisible = true
                binding.btnDownload.isEnabled = false
                loadCityWithoutCoroutine {
                    loadDataWithoutCoroutines(1, it)
                }
            }

            1 -> {
                val city = obj as String
                binding.tvCity.text = city
                loadTempWithoutCoroutine(city) {
                    loadDataWithoutCoroutines(2, it)
                }
            }

            2 -> {
                val temp = obj as Int
                binding.tvTemp.text = temp.toString()

                binding.progressBar.isVisible = false
                binding.btnDownload.isEnabled = true
                Log.d("TAG", "Loading finished!")
            }
        }
    }

    private fun loadCityWithoutCoroutine(callback: (String) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke("Moscow")
        }, 2000)
    }

    private fun loadTempWithoutCoroutine(city: String, callback: (Int) -> Unit) {
        Toast.makeText(
            this,
            "Loading temp for city: $city",
            Toast.LENGTH_SHORT
        ).show()

        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke(9)
        }, 2000)
    }
}