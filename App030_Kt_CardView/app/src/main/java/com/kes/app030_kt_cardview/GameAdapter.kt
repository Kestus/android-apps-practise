package com.kes.app030_kt_cardview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kes.app030_kt_cardview.models.Game

class GameAdapter(private val games: ArrayList<Game>): RecyclerView.Adapter<GameAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]
        holder.imageView.setImageResource(game.imageSrc)
        holder.titleView.text = game.name
    }

    override fun getItemCount(): Int = games.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.card_image)
        val titleView: TextView = view.findViewById(R.id.card_text)
        
        init {
            view.setOnClickListener() {
                Toast.makeText(
                    view.context,
                    games[adapterPosition].name,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}