package com.kes.app045_kt_currencies.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kes.app045_kt_currencies.R
import com.kes.app045_kt_currencies.databinding.ActivityCurrencyBinding
import com.kes.app045_kt_currencies.domain.model.CurrencyItem
import com.kes.app045_kt_currencies.presentation.viewModel.AppViewModelFactory
import com.kes.app045_kt_currencies.presentation.viewModel.CurrencyViewModel

class CurrencyActivity : AppCompatActivity() {

    private val binding: ActivityCurrencyBinding by lazy {
        ActivityCurrencyBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(ITEM, CurrencyItem::class.java)
        } else {
            intent.getParcelableExtra(ITEM)
        }

        viewModel = AppViewModelFactory(application, item).create(CurrencyViewModel::class.java)
        viewModel.launchPriceUpdateWork()

    }



    companion object {
        private const val ITEM = "item"

        fun newIntent(context: Context, item: CurrencyItem): Intent {
            return Intent(context, CurrencyActivity::class.java).apply {
                putExtra(ITEM, item)
            }
        }

    }
}