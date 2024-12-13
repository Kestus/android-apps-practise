package com.kes.app049_kt_coroutineflow

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.kes.app049_kt_coroutineflow.databinding.ActivityUsersBinding
import java.util.Locale

class UsersActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityUsersBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[UsersViewModel::class.java]
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

        setOnClickListeners()
        observeViewModel()
    }

    private fun setOnClickListeners() {
        binding.btnAdd.setOnClickListener {
            binding.etInput.text.toString()
                .trim()
                .takeIf { it.isNotBlank() }
                ?.let { input ->
                    viewModel.addUser(input.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.US)
                        else it.toString()
                    })
                }
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, UsersActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) {
            binding.tvUsers.text = it.joinToString()
        }
    }
}