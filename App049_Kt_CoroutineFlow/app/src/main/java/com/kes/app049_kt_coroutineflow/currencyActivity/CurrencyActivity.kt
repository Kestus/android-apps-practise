package com.kes.app049_kt_coroutineflow.currencyActivity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kes.app049_kt_coroutineflow.databinding.ActivityCurrencyBinding
import com.kes.app049_kt_coroutineflow.databinding.CurrencyItemBinding
import kotlinx.coroutines.launch

class CurrencyActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCurrencyBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CurrencyViewModel::class.java]
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

        observeViewModel()
        binding.fabRefresh.setOnClickListener {
            viewModel.refreshList()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    binding.llContainer.removeAllViews()
                    binding.progressBar.visibility = View.GONE
                    binding.fabRefresh.visibility = View.GONE
                    when (it) {
                        is CurrencyState.Initial -> {}

                        is CurrencyState.Loading -> binding.progressBar.visibility =
                            View.VISIBLE

                        is CurrencyState.Content -> {
                            for (currency in it.currencyList) {
                                val itemBinding = CurrencyItemBinding.inflate(layoutInflater)
                                itemBinding.itemName.text = currency.name
                                itemBinding.itemPrice.text = currency.price.toString()
                                binding.llContainer.addView(itemBinding.root)
                            }
                            binding.fabRefresh.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }


}