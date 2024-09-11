package com.kes.app039_kt_notes.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kes.app039_kt_notes.databinding.NoteLayoutBinding
import com.kes.app039_kt_notes.fragments.HomeFragmentDirections
import com.kes.app039_kt_notes.models.Note
import kotlin.random.Random

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    class ViewHolder(val binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
           return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NoteLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedNote = differ.currentList[position]

        holder.binding.noteTitle.text = selectedNote.title
        holder.binding.noteBody.text = selectedNote.body

        val color = Color.argb(
            255,
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )

        holder.binding.noteColor.setBackgroundColor(color)
        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(selectedNote)
            it.findNavController().navigate(direction)
        }
    }


}