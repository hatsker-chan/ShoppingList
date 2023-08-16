package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val itemLayoutId = when (viewType) {
            ENABLED_VIEW_TYPE -> R.layout.item_shop_enabled
            DISABLED_VIEW_TYPE -> R.layout.item_shop_disabled
            else -> throw RuntimeException("unknown viewType: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            itemLayoutId,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) {
            ENABLED_VIEW_TYPE
        } else {
            DISABLED_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.tvItemName.text = shopItem.name
        holder.tvItemCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    companion object {
        const val ENABLED_VIEW_TYPE = 0
        const val DISABLED_VIEW_TYPE = 1

        const val MAX_PULL_SIZE = 10
    }
}