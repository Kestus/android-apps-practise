package com.kes.app037_kt_contactmanager.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kes.app037_kt_contactmanager.R
import com.kes.app037_kt_contactmanager.databinding.CardItemBinding
import com.kes.app037_kt_contactmanager.room.Contact

class RecyclerViewAdapter(
    private val contacts: List<Contact>,
    private val clickListener: (Contact) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.card_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position], clickListener)
    }

    override fun getItemCount(): Int = contacts.size

    class ViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact, clickListener: (Contact) -> Unit) {
            binding.cardName.text = contact.name
            binding.cardEmail.text = contact.email

            binding.cardItemLayout.setOnClickListener() {
                clickListener(contact)
            }
        }
    }


}