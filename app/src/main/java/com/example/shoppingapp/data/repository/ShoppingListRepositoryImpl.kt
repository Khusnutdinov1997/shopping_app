package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.shopping_list_item.ShoppingListDao
import com.example.shoppingapp.data.shopping_list_item.ShoppingListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject  constructor(
    private val shoppingDao: ShoppingListDao
): ShoppingListRepository {
    override suspend fun insertItem(shoppingListItem: ShoppingListItem) {
        shoppingDao.insertItem(shoppingListItem)
    }

    override suspend fun deleteItem(shoppingListItem: ShoppingListItem) {
        shoppingDao.deleteItem(shoppingListItem)
    }

    override suspend fun deleteTaskItems(idList: Int) {
        shoppingDao.deleteTaskItems(idList)
    }

    override suspend fun update(shoppingListItem: ShoppingListItem) {
        shoppingDao.update(shoppingListItem)
    }

    override suspend fun deleteShoppingList(shoppingListItem: ShoppingListItem) {
        shoppingDao.deleteShoppingList(shoppingListItem)
    }

    override fun getAllItems(): Flow<List<ShoppingListItem>> {
        return shoppingDao.getAllItems()
    }

    override suspend fun getShoppingListItemById(idList: Int): ShoppingListItem {
        return shoppingDao.getShoppingListItemById(idList)
    }

    override suspend fun updateShoppingListCount(idList: Int, increment: Int) {
        shoppingDao.updateShoppingListCount(idList, increment)
    }

}