package com.kes.app048_kt_factorial

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.kes.app048_kt_factorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
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

        addClickListener()
        observeViewModel()
    }

    private fun addClickListener() {
        binding.btnSubmit.setOnClickListener {
            viewModel.submit(binding.etInput.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            binding.apply {
                progressBar.visibility = View.GONE
                btnSubmit.isEnabled = true
                when (it) {
                    is Error -> {
                        etInput.error = it.message
                    }
                    is Factorial -> {
                        twOutput.text = it.value
                    }
                    is Progress -> {
                        progressBar.visibility = View.VISIBLE
                        btnSubmit.isEnabled = false
                    }
                }
            }
        }
    }
}