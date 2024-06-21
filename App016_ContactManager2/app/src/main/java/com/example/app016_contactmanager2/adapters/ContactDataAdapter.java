package com.example.app016_contactmanager2.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app016_contactmanager2.R;
import com.example.app016_contactmanager2.databinding.ContactListItemBinding;
import com.example.app016_contactmanager2.db.entities.Contact;

import java.util.ArrayList;

public class ContactDataAdapter extends RecyclerView.Adapter<ContactDataAdapter.ContactViewHolder> {

    private ArrayList<Contact> contacts;

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.contact_list_item, parent, false);
//        return new ContactViewHolder(itemView);

        ContactListItemBinding contactListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.contact_list_item,
                parent,
                false
        );

        return new ContactViewHolder(contactListItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
//        holder.name.setText(contact.getName());
//        holder.email.setText(contact.getEmail());

        holder.contactListItemBinding.setContact(contact);
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyItemRangeChanged(0, contacts.size());
    }



    class ContactViewHolder extends RecyclerView.ViewHolder {
//        private TextView name;
//        private TextView email;
        private ContactListItemBinding contactListItemBinding;

        public ContactViewHolder(@NonNull ContactListItemBinding contactListItemBinding) {
            super(contactListItemBinding.getRoot());
            this.contactListItemBinding = contactListItemBinding;
//            this.name = itemView.findViewById(R.id.item_name);
//            this.email = itemView.findViewById(R.id.item_email);
        }
    }
}
