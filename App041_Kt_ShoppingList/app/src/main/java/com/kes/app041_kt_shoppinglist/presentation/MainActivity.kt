package com.kes.app041_kt_shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kes.app041_kt_shoppinglist.R
import com.kes.app041_kt_shoppinglist.data.AppDatabase
import com.kes.app041_kt_shoppinglist.data.ShopItemDAO
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var dao: ShopItemDAO
    private lateinit var repository: ShopListRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dao = AppDatabase.getInstance(this).shopItemDAO
        repository = ShopListRepositoryImpl(dao)

        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            Log.d("TAG", it.toString())
        }
    }
}