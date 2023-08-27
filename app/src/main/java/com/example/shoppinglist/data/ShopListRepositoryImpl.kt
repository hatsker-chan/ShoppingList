package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopListDao = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun getShopItem(shopItemId: Int): ShopItem {
        return mapper.mapDbModelToEntity(shopListDao.getShopItem(shopItemId))
    }

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopList()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }


    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }
}