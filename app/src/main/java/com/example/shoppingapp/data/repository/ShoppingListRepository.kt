package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.shopping_list_item.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    suspend fun insertItem(shoppingListItem: ShoppingListItem)
    suspend fun deleteItem(shoppingListItem: ShoppingListItem)
    suspend fun deleteTaskItems(idList: Int)
    suspend fun update(shoppingListItem: ShoppingListItem)
    suspend fun deleteShoppingList(shoppingListItem: ShoppingListItem)
    fun getAllItems():  Flow<List<ShoppingListItem>>
    suspend fun getShoppingListItemById(idList: Int): ShoppingListItem
    suspend fun updateShoppingListCount(idList: Int, increment: Int)
}