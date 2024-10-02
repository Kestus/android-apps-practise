package com.kes.app041_kt_shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kes.app041_kt_shoppinglist.R
import com.kes.app041_kt_shoppinglist.data.AppDatabase
import com.kes.app041_kt_shoppinglist.data.ShopItemDAO
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl
import com.kes.app041_kt_shoppinglist.domain.fragments.ShopItemFragment
import com.kes.app041_kt_shoppinglist.presentation.adapter.ShopListAdapter
import com.kes.app041_kt_shoppinglist.presentation.viewModel.MainViewModel
import com.kes.app041_kt_shoppinglist.presentation.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity(), ShopItemFragment.OnFragmentFinishedListener {

    private lateinit var dao: ShopItemDAO
    private lateinit var repository: ShopListRepositoryImpl

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    private lateinit var btnAdd: FloatingActionButton
    private var shopItemContainer: FragmentContainerView? = null

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

        // Load Shop item fragment, if in landscape mode
        shopItemContainer = findViewById(R.id.shop_item_container)

        btnAdd = findViewById(R.id.btn_add_shop_item)
        btnAdd.setOnClickListener {
            if (!isInLandscape()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }

    }

    private fun isInLandscape() = shopItemContainer != null

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

        setupItemClickListener()
        setupItemLongClickListener()
        setupItemSwipeListener(rvShopList)
    }

    private fun setupItemClickListener() {
        adapter.onShopItemClickListener = {
            if (!isInLandscape()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupItemLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeActiveState(it)
        }
    }

    private fun setupItemSwipeListener(recyclerView: RecyclerView) {
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

    private fun launchFragment(fragment: ShopItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onFinished() {
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}
