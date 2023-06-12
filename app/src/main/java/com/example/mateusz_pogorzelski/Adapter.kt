package com.example.mateusz_pogorzelski

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

// ADAPTER FOR RECYCLER VIEW
class Adapter(val context: Context, val items: List<ItemsInterface>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val viewholder = holder as ViewHolder

        holder.usernameText.text = "Username: " + item.username
        holder.thumbnailImage.load(item.thumbnail) // USING COIL
        holder.tagsText.text = "Tags: "+ item.tagList


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImage = itemView.findViewById<ImageView>(R.id.itemimage)
        val usernameText = itemView.findViewById<TextView>(R.id.usernameText)
        val tagsText = itemView.findViewById<TextView>(R.id.tagsText)
    }
}