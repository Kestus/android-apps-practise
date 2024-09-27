package com.kes.app041_kt_shoppinglist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kes.app041_kt_shoppinglist.R
import com.kes.app041_kt_shoppinglist.domain.ShopItem

class ShopListAdapter
    : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    enum class ViewTypes(val value: Int) {
        ACTIVE(R.layout.shop_item_active),
        INACTIVE(R.layout.shop_item_inactive)
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item: ShopItem = getItem(position)

        holder.apply {
            tvName.text = item.name
            tvCount.text = item.count.toString()

            view.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(item)
                true
            }

            view.setOnClickListener {
                onShopItemClickListener?.invoke(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.active) {
            ViewTypes.ACTIVE.value
        } else {
            ViewTypes.INACTIVE.value
        }
    }
}