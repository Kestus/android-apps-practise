package com.kes.app041_kt_shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kes.app041_kt_shoppinglist.R
import com.kes.app041_kt_shoppinglist.data.AppDatabase
import com.kes.app041_kt_shoppinglist.data.ShopItemDAO
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl
import com.kes.app041_kt_shoppinglist.databinding.ActivityMainBinding
import com.kes.app041_kt_shoppinglist.presentation.adapter.ShopListAdapter
import com.kes.app041_kt_shoppinglist.presentation.viewModel.MainViewModel
import com.kes.app041_kt_shoppinglist.presentation.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity(), ShopItemFragment.OnFragmentFinishedListener {

    private lateinit var repository: ShopListRepositoryImpl

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Database
        repository = ShopListRepositoryImpl(application)

        // RecyclerView
        adapter = ShopListAdapter()
        setupRecyclerView()

        // ViewModel
        setupViewModel()

        // Listeners
        addItemClickListener()
        addFabClickListener()
        addItemLongClickListener()
        addItemSwipeListener()
    }

    private fun isInLandscape() = binding.shopItemContainer != null

    private fun setupRecyclerView() {
        binding.rvShopList.adapter = adapter
        for (viewTypes in ShopListAdapter.ViewTypes.entries) {
            binding.rvShopList.recycledViewPool.setMaxRecycledViews(
                viewTypes.value,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
    }

    private fun setupViewModel() {
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun addItemClickListener() {
        adapter.onShopItemClickListener = {
            if (!isInLandscape()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun addFabClickListener() {
        binding.btnAddShopItem.setOnClickListener {
            if (!isInLandscape()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun addItemLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeActiveState(it)
        }
    }

    private fun addItemSwipeListener() {
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
        itemTouchHelper.attachToRecyclerView(binding.rvShopList)
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