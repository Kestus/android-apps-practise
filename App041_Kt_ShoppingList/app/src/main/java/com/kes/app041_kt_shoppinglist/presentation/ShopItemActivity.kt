package com.kes.app041_kt_shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.kes.app041_kt_shoppinglist.R
import com.kes.app041_kt_shoppinglist.data.AppDatabase
import com.kes.app041_kt_shoppinglist.data.ShopItemDAO
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl
import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.presentation.viewModel.MainViewModelFactory
import com.kes.app041_kt_shoppinglist.presentation.viewModel.ShopItemViewModel
import com.kes.app041_kt_shoppinglist.presentation.viewModel.ShopItemViewModelFactory

class ShopItemActivity : AppCompatActivity() {

    // ViewModel
    private lateinit var viewModel: ShopItemViewModel

    // DB
    private lateinit var db: AppDatabase
    private lateinit var dao: ShopItemDAO
    private lateinit var repository: ShopListRepositoryImpl

    // Views
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSubmit: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var screenMode: String
    private var shopItemID: Int = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        parseIntent()
        initViews()

        // DB and VM
        initRepository()
        viewModel = ShopItemViewModelFactory(repository).create(ShopItemViewModel::class.java)

        initInputErrorListeners()
        addLoadingStateObserver()
        addFinishedStateObserver()

        // Select this screen mode
        when (screenMode) {
            MODE_ADD -> initAddMode()
            MODE_EDIT -> initEditMode()
        }
    }

    private fun initRepository() {
        db = AppDatabase.getInstance(this)
        dao = db.shopItemDAO
        repository = ShopListRepositoryImpl(dao)
    }

    private fun addFinishedStateObserver() {
        viewModel.finished.observe(this) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addLoadingStateObserver() {
        viewModel.loading.observe(this) {
            when (it) {
                true -> progressBar.visibility = View.VISIBLE
                false -> progressBar.visibility = View.GONE
            }
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is missing")
        }

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item ID is missing")
            }

            shopItemID = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        btnSubmit = findViewById(R.id.btn_submit)
        progressBar = findViewById(R.id.progress_bar)
    }

    private fun initAddMode() {
        btnSubmit.setOnClickListener {
            viewModel.addShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun initEditMode() {
        btnSubmit.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }

        viewModel.getShopItem(shopItemID)
        viewModel.currentShopItem.observe(this) {
            etName.setText(it?.name)
            etCount.setText(it?.count.toString())
        }
    }

    private fun initInputErrorListeners() {
        viewModel.errorInputName.observe(this) {
            when (it) {
                true -> tilName.error = ERROR_MESSAGE_NAME
                false -> tilName.error = null
            }
        }

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }
        })


        viewModel.errorInputCount.observe(this) {
            when (it) {
                true -> tilCount.error = ERROR_MESSAGE_COUNT
                false -> tilCount.error = null
            }
        }

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }
        })
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

        private const val ERROR_MESSAGE_NAME = "Incorrect name"
        private const val ERROR_MESSAGE_COUNT = "Incorrect count"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, itemId)

            return intent
        }
    }
}