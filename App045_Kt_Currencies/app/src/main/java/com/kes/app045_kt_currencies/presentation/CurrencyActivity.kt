package com.kes.app045_kt_currencies.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kes.app045_kt_currencies.R
import com.kes.app045_kt_currencies.databinding.ActivityCurrencyBinding
import com.kes.app045_kt_currencies.presentation.adapters.PriceListAdapter
import com.kes.app045_kt_currencies.presentation.viewModel.AppViewModelFactory
import com.kes.app045_kt_currencies.presentation.viewModel.CurrencyViewModel

class CurrencyActivity : AppCompatActivity() {

    private val binding: ActivityCurrencyBinding by lazy {
        ActivityCurrencyBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: CurrencyViewModel
    private val adapter by lazy { PriceListAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currencyCode = intent.getStringExtra(ITEM_ID)

        viewModel =
            AppViewModelFactory(application, currencyCode).create(CurrencyViewModel::class.java)
        binding.recyclerView.adapter = adapter

        bindCurrencyItem()
        observePriceList()
        setupFavBtnClickListener()
        setupOnItemClickListener()

    }

    private fun bindCurrencyItem() {
        viewModel.currentItem.observe(this) {
            it?.let {
                binding.currencyName.text = it.name
                binding.currencyCode.text = it.code.uppercase()
                binding.currencyDate.text = it.pricesUpdatedAt
            }
            observeLoading(it == null)
        }
    }

    private fun observePriceList() {
        viewModel.priceList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun observeLoading(boolean: Boolean) {
        binding.progressBar.visibility = if (boolean) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    private fun setupFavBtnClickListener() {
        binding.btnFav.setOnClickListener { viewModel.toggleFavourite() }
        viewModel.isFavourite.observe(this) {
            val src = if (it) {
                R.drawable.ic_star_active
            } else {
                R.drawable.ic_star_border
            }
            binding.btnFav.setImageResource(src)
        }
    }

    private fun setupOnItemClickListener() {
        adapter.onItemClickListener = {
            startActivity(newIntent(this, it.currencyCode))
        }
    }

    companion object {
        private const val ITEM_ID = "currency_id"

        fun newIntent(context: Context, currencyCode: String): Intent {
            return Intent(context, CurrencyActivity::class.java).apply {
                putExtra(ITEM_ID, currencyCode)
            }
        }

    }
}