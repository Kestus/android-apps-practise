package com.kes.app045_kt_currencies.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kes.app045_kt_currencies.MainApplication
import com.kes.app045_kt_currencies.R
import com.kes.app045_kt_currencies.databinding.FragmentPriceListBinding
import com.kes.app045_kt_currencies.presentation.adapters.PriceListAdapter
import com.kes.app045_kt_currencies.presentation.viewModel.AppViewModelFactory
import com.kes.app045_kt_currencies.presentation.viewModel.PriceListViewModel
import javax.inject.Inject

class PriceListFragment : Fragment() {
    private val component by lazy {
        (requireActivity().application as MainApplication).component
            .activityComponentFactory()
            .create(args.currencyCode)
    }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel: PriceListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PriceListViewModel::class.java]
    }

    @Inject
    lateinit var adapter: PriceListAdapter

    private var _binding: FragmentPriceListBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("_binding == null")


    private val args by navArgs<PriceListFragmentArgs>()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPriceListBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        bindCurrencyItem()
        observePriceList()
        setupFavBtnClickListener()
        setupOnItemClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindCurrencyItem() {
        viewModel.currencyItem.observe(viewLifecycleOwner) {
            it?.let {
                binding.currencyName.text = it.name
                binding.currencyCode.text = it.code.uppercase()
                binding.currencyDate.text = it.pricesUpdatedAt
            }
            observeLoading(it == null)
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
        viewModel.isFavourite.observe(viewLifecycleOwner) {
            it?.let {
                val src = if (it) {
                    R.drawable.ic_star_active
                } else {
                    R.drawable.ic_star_border
                }
                binding.btnFav.setImageResource(src)
            }
        }
    }

    private fun setupOnItemClickListener() {
        adapter.onItemClickListener = {
            findNavController().navigate(
                PriceListFragmentDirections.actionPriceListFragmentSelf(it.currencyCode),
                NavOptions.Builder().setLaunchSingleTop(true).build()
            )
        }
    }

    private fun observePriceList() {
        viewModel.priceList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}