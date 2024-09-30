package com.kes.app041_kt_shoppinglist.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kes.app041_kt_shoppinglist.R
import com.kes.app041_kt_shoppinglist.data.AppDatabase
import com.kes.app041_kt_shoppinglist.data.ShopItemDAO
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl
import com.kes.app041_kt_shoppinglist.presentation.adapter.ShopListAdapter
import com.kes.app041_kt_shoppinglist.presentation.viewModel.MainViewModel
import com.kes.app041_kt_shoppinglist.presentation.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var dao: ShopItemDAO
    private lateinit var repository: ShopListRepositoryImpl

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    private lateinit var btnAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Database
        dao = AppDatabase.getInstance(this).shopItemDAO
        repository = ShopListRepositoryImpl(dao)

        // ViewModel/Recycler
        setupRecyclerView()
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        btnAdd = findViewById(R.id.btn_add_shop_item)
        btnAdd.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }

    }

    private fun setupRecyclerView() {
        val rvShopList: RecyclerView = findViewById(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rvShopList.adapter = adapter
        for (viewTypes in ShopListAdapter.ViewTypes.entries) {
            rvShopList.recycledViewPool.setMaxRecycledViews(
                viewTypes.value,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        setupClickListener()
        setupLongClickListener()
        setupSwipeListener(rvShopList)
    }

    private fun setupClickListener() {
        adapter.onShopItemClickListener = {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeActiveState(it)
        }
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
