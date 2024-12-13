package com.kes.app049_kt_coroutineflow.currencyActivity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.kes.app049_kt_coroutineflow.databinding.ActivityCurrencyBinding
import com.kes.app049_kt_coroutineflow.databinding.CurrencyItemBinding

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

        viewModel.state.observe(this) {
            binding.container.removeAllViews()
            binding.progressBar.visibility = View.GONE
            when (it) {
                is CurrencyState.Content -> {
                    for (currency in it.currencyList) {
                        val itemBinding = CurrencyItemBinding.inflate(layoutInflater)
                        itemBinding.itemName.text = currency.name
                        itemBinding.itemPrice.text = currency.price.toString()
                        binding.container.addView(itemBinding.root)
                    }
                }

                is CurrencyState.Initial -> {}
                is CurrencyState.Loading -> binding.progressBar.visibility = View.VISIBLE
            }
        }

    }
}