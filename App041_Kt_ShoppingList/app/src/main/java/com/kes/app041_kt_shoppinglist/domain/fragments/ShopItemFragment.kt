package com.kes.app041_kt_shoppinglist.domain.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.kes.app041_kt_shoppinglist.R
import com.kes.app041_kt_shoppinglist.data.AppDatabase
import com.kes.app041_kt_shoppinglist.data.ShopItemDAO
import com.kes.app041_kt_shoppinglist.data.ShopListRepositoryImpl
import com.kes.app041_kt_shoppinglist.domain.ShopItem
import com.kes.app041_kt_shoppinglist.presentation.viewModel.ShopItemViewModel
import com.kes.app041_kt_shoppinglist.presentation.viewModel.ShopItemViewModelFactory

class ShopItemFragment() : Fragment() {

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

    // args
    private lateinit var screenMode: String
    private var shopItemID: Int = ShopItem.UNDEFINED_ID

    private lateinit var onFragmentFinished: OnFragmentFinishedListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        // DB and VM
        initRepository()
        viewModel = ShopItemViewModelFactory(repository).create(ShopItemViewModel::class.java)

        if (context is OnFragmentFinishedListener) {
            onFragmentFinished = context
        } else throw RuntimeException("Activity must implement: OnFragmentFinishedListener")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initInputErrorListeners()
        addLoadingStateObserver()
        addFinishedStateObserver()

        // Select correct screen mode
        when (screenMode) {
            MODE_ADD -> initAddMode()
            MODE_EDIT -> initEditMode()
        }
    }

    private fun initRepository() {
        db = context?.let { AppDatabase.getInstance(it) }!!
        dao = db.shopItemDAO
        repository = ShopListRepositoryImpl(dao)
    }

    private fun addLoadingStateObserver() {
        viewModel.loading.observe(viewLifecycleOwner) {
            when (it) {
                true -> progressBar.visibility = View.VISIBLE
                false -> progressBar.visibility = View.GONE
            }
        }
    }

    private fun addFinishedStateObserver() {
        viewModel.finished.observe(viewLifecycleOwner) {
            Log.d("TAG", "addFinishedStateObserver: finished")
            Log.d("TAG", "addFinishedStateObserver: $onFragmentFinished")
            onFragmentFinished.onFinished()
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is missing")
        }

        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item ID is missing")
            }

            shopItemID = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        btnSubmit = view.findViewById(R.id.btn_submit)
        progressBar = view.findViewById(R.id.progress_bar)
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
        viewModel.currentShopItem.observe(viewLifecycleOwner) {
            etName.setText(it?.name)
            etCount.setText(it?.count.toString())
        }
    }

    private fun initInputErrorListeners() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
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


        viewModel.errorInputCount.observe(viewLifecycleOwner) {
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

    interface OnFragmentFinishedListener {
        fun onFinished()
    }

    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

        private const val ERROR_MESSAGE_NAME = "Incorrect name"
        private const val ERROR_MESSAGE_COUNT = "Incorrect count"

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(itemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, itemId)
                }
            }
        }
    }
}