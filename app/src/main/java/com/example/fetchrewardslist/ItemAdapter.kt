/**
 * This program houses my adapter class for the recycler view in MainActivity.kt. This adapter handles all
 * necessary functions for connecting each instance in a list of items to a card view in the recycler view
 *
 * Fetch Android Assignment
 * No sources to cite.
 *
 * @author Aaron Miller
 * @version v1.0 08/22/22
 */
package com.example.fetchrewardslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView



class ItemAdapter(var context: Context, var items: List<Item>): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(view:View) : RecyclerView.ViewHolder(view) {
        var itemID: TextView
        var itemListID: TextView
        var nameID: TextView

        init {
            itemID = itemView.findViewById(R.id.idTextView)
            itemListID = itemView.findViewById(R.id.listIdTextView)
            nameID = itemView.findViewById(R.id.nameTextView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemID.text = "ID: " + items[position].id
        holder.itemListID.text = "List ID: " + items[position].listId
        holder.nameID.text = "Name: " + items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}