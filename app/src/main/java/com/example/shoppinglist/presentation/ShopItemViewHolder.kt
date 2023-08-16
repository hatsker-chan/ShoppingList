package com.example.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R

class ShopItemViewHolder(itemView: View) : ViewHolder(itemView) {
    val tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
    val tvItemCount: TextView = itemView.findViewById(R.id.tv_item_count)
}