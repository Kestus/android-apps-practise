package com.kes.app041_kt_shoppinglist.presentation

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.BackEventCompat
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kes.app041_kt_shoppinglist.R
import com.kes.app041_kt_shoppinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    enum class ViewTypes(val value: Int) {
        ACTIVE(R.layout.shop_item_active),
        INACTIVE(R.layout.shop_item_inactive)
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }

    var shopList: List<ShopItem> = listOf()
        set(value) {
            val callback = ShopListDiffCallback(shopList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item: ShopItem = shopList[position]

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

    override fun getItemCount(): Int = shopList.size

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.active) {
            ViewTypes.ACTIVE.value
        } else {
            ViewTypes.INACTIVE.value
        }
    }

    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_item_name)
        val tvCount: TextView = view.findViewById(R.id.tv_item_count)
    }
}