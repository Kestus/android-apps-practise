package com.kes.app045_kt_currencies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kes.app045_kt_currencies.databinding.PriceCardBinding
import com.kes.app045_kt_currencies.domain.model.RelativePriceItem
import java.util.Locale

class PriceListAdapter :
    ListAdapter<RelativePriceItem, PriceListAdapter.ViewHolder>(DiffCallback()) {

    var onItemClickListener: ((RelativePriceItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PriceCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding

        binding.currencyCode.text = item.currencyCode.uppercase()
        binding.currencyPrice.text = String.format(Locale.US, "%.5f", item.price)

        binding.root.apply {
            setOnClickListener { onItemClickListener?.invoke(item) }
        }
    }

    class ViewHolder(val binding: PriceCardBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback() : DiffUtil.ItemCallback<RelativePriceItem>() {
        override fun areItemsTheSame(
            oldItem: RelativePriceItem,
            newItem: RelativePriceItem
        ): Boolean {
            return oldItem.currencyCode == newItem.currencyCode
        }

        override fun areContentsTheSame(
            oldItem: RelativePriceItem,
            newItem: RelativePriceItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}