package com.example.shoppinglist.domain

interface ShopListRepository {
    fun getShopItem(shopIteId: Int): ShopItem

    fun getShopList(): List<ShopItem>

    fun addShopItem(shopItem: ShopItem)

    fun removeShopItem(shopItem: ShopItem)

    fun editShopItem(ShopItemId: Int)
}