package com.kes.app045_kt_currencies.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.kes.app045_kt_currencies.databinding.ActivityMainBinding
import com.kes.app045_kt_currencies.presentation.adapters.CurrencyListAdapter
import com.kes.app045_kt_currencies.presentation.viewModel.AppViewModelFactory
import com.kes.app045_kt_currencies.presentation.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        AppViewModelFactory(application).create(MainViewModel::class.java)
    }
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

//        viewModel.deleteAll()

        setupAdapter()
        binding.recyclerView.adapter = adapter

        observeCurrencyList()
        setupSearchInputListener()
        setupItemOnClickListener()
    }

    private fun setupAdapter() {
        adapter = CurrencyListAdapter()
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                if (fromPosition >= toPosition) {
                    binding.recyclerView.scrollToPosition(toPosition)
                } else {
                    binding.recyclerView.scrollToPosition(fromPosition)
                }
            }
        })
    }

    private fun observeCurrencyList() {
        viewModel.filteredList.observe(this) {
            adapter.submitList(it)
        }

        viewModel.loading.observe(this) {
            val visibility = if (it) View.VISIBLE
            else View.GONE
            binding.progressBar.visibility = visibility
        }
    }

    private fun setupItemOnClickListener() {
        adapter.onItemClickListener = {
            startActivity(CurrencyActivity.newIntent(this, it.code))
        }
    }

    private fun setupSearchInputListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchInput(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                // TODO("Not yet implemented")
            }
        })
    }
}