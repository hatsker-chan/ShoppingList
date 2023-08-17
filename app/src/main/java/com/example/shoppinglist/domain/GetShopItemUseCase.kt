package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(shopItemId: Int): ShopItem{
        return shopListRepository.getShopItem(shopItemId)
    }
}