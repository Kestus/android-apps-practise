package com.kes.app041_kt_shoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kes.app041_kt_shoppinglist.MainApplication
import com.kes.app041_kt_shoppinglist.databinding.FragmentShopItemBinding
import com.kes.app041_kt_shoppinglist.domain.model.ShopItem
import com.kes.app041_kt_shoppinglist.presentation.viewModel.AppViewModelFactory
import com.kes.app041_kt_shoppinglist.presentation.viewModel.ShopItemViewModel
import javax.inject.Inject

class ShopItemFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as MainApplication).component
    }

    // ViewModel
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val shopItemViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class.java]
    }

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

    // args
    private lateinit var screenMode: String
    private var shopItemID: Int = ShopItem.UNDEFINED_ID

    private lateinit var onFragmentFinished: OnFragmentFinishedListener


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
    ): View {
        _binding = FragmentShopItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = shopItemViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        addTextChangedListeners()
        addLoadingStateObserver()
        addFinishedStateObserver()

        // Select correct screen mode
        selectCorrectMode()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addLoadingStateObserver() {
        shopItemViewModel.loading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun addFinishedStateObserver() {
        shopItemViewModel.finished.observe(viewLifecycleOwner) {
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

    private fun selectCorrectMode() {
        when (screenMode) {
            MODE_ADD -> initAddMode()
            MODE_EDIT -> initEditMode()
        }
    }

    private fun initAddMode() {
        binding.apply {
            btnSubmit.setOnClickListener {
//                shopItemViewModel.addShopItem(etName.text.toString(), etCount.text.toString())
                // Add with content provider
                shopItemViewModel.addShopItemProvider(
                    etName.text.toString(),
                    etCount.text.toString()
                )

            }
        }
    }

    private fun initEditMode() {
        shopItemViewModel.getShopItem(shopItemID)
        binding.apply {
            btnSubmit.setOnClickListener {
//                shopItemViewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())

                // Edit with content provider
                shopItemViewModel.editShopItemProvider(
                    etName.text?.toString(),
                    etCount.text?.toString()
                )
            }
        }
    }

    private fun addTextChangedListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetErrorInputName()
            }
        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetErrorInputCount()
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