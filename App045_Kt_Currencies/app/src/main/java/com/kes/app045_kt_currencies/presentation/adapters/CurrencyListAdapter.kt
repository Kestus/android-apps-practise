package com.kes.app045_kt_currencies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kes.app045_kt_currencies.databinding.CurrencyCardBinding
import com.kes.app045_kt_currencies.domain.model.CurrencyItem

class CurrencyListAdapter :
    ListAdapter<CurrencyItem, CurrencyListAdapter.ViewHolder>(DiffCallback()) {

    var onItemClickListener: ((CurrencyItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CurrencyCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding

        binding.currencyCode.text = item.code.uppercase()
        binding.currencyName.text = item.name

        binding.root.apply {
            setOnClickListener { onItemClickListener?.invoke(item) }
        }
    }

    class ViewHolder(val binding: CurrencyCardBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback() : DiffUtil.ItemCallback<CurrencyItem>() {
        override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
            return oldItem == newItem
        }
    }
}