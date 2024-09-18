package com.kes.app040_kt_journal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kes.app040_kt_journal.R
import com.kes.app040_kt_journal.databinding.JournalItemBinding
import com.kes.app040_kt_journal.models.Journal

class JournalRecyclerAdapter(
    private val context: Context,
    private val journals: List<Journal>
) : RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: JournalItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = JournalItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(journals[position])
    }

    override fun getItemCount(): Int = journals.size


    inner class ViewHolder(binding: JournalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: Journal) {
            binding.journal = journal

            Glide.with(context)
                .load(journal.imageUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .fitCenter()
                .into(binding.itemImage)
        }
    }
}