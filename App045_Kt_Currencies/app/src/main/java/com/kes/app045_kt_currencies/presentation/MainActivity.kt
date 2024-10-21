package com.kes.app045_kt_currencies.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kes.app045_kt_currencies.R
import com.kes.app045_kt_currencies.databinding.ActivityMainBinding
import com.kes.app045_kt_currencies.databinding.CurrencyCardBinding
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.presentation.adapters.CurrencyListAdapter

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CurrencyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = CurrencyListAdapter()

        binding.recyclerView.adapter = adapter

        viewModel.currencyList.observe(this) {
            adapter.submitList(it)
        }

        viewModel.listIsEmpty.observe(this) {
            val visibility = if (it) View.GONE
            else View.VISIBLE

            binding.progressBar.visibility = visibility
        }


    }
}