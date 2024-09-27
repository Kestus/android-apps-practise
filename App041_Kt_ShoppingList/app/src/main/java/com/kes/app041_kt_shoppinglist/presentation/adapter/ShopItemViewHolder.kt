package com.kes.app041_kt_shoppinglist.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kes.app041_kt_shoppinglist.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName: TextView = view.findViewById(R.id.tv_item_name)
    val tvCount: TextView = view.findViewById(R.id.tv_item_count)
}