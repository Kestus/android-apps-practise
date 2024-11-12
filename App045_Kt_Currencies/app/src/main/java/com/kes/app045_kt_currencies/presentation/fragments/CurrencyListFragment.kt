package com.kes.app045_kt_currencies.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kes.app045_kt_currencies.databinding.FragmentCurrencyListBinding
import com.kes.app045_kt_currencies.presentation.adapters.CurrencyListAdapter
import com.kes.app045_kt_currencies.presentation.viewModel.AppViewModelFactory
import com.kes.app045_kt_currencies.presentation.viewModel.CurrencyListViewModel

class CurrencyListFragment : Fragment() {

    private var _binding: FragmentCurrencyListBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("_binding == null")

    private lateinit var viewModel: CurrencyListViewModel
    private lateinit var adapter: CurrencyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCurrencyListBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = AppViewModelFactory(
            requireActivity().application
        ).create(CurrencyListViewModel::class.java)
        setupAdapter()
        binding.recyclerView.adapter = adapter
        observeCurrencyList()
        setupItemOnClickListener()
        setupSearchInput()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAdapter() {
        adapter = CurrencyListAdapter()
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                if (fromPosition >= toPosition) {
                    binding.recyclerView.scrollToPosition(toPosition)
                } else {
                    binding.recyclerView.scrollToPosition(fromPosition)
                }
            }
        })
    }

    private fun observeCurrencyList() {
        viewModel.filteredList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            val visibility = if (it) View.VISIBLE
            else View.GONE
            binding.progressBar.visibility = visibility
        }
    }

    private fun setupItemOnClickListener() {
        adapter.onItemClickListener = {
           findNavController().navigate(
               CurrencyListFragmentDirections.actionCurrencyListFragmentToPriceListFragment(it.code)
           )
        }
    }

    private fun setupSearchInput() {
        setupSearchInputListener()
        setupClearSearchButton()
    }

    private fun setupClearSearchButton() {
        binding.apply {
            btnClearSearch.setOnClickListener {
                etSearch.text?.clear()
                etSearch.clearFocus()
                viewModel.clearSearchInput()
            }
            viewModel.searchInput.observe(viewLifecycleOwner) {
                btnClearSearch.visibility = if (it == null) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }
    }

    private fun setupSearchInputListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchInput(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                // TODO("Not yet implemented")
            }
        })
    }
}