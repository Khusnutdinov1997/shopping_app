package com.example.shoppingapp.utils

import com.example.shoppingapp.data.shopping_list_item.ShoppingListItem

object ProgressHelper {
    fun getProgress(itemsCount: Int, selectedItemsCount: Int): Float{
        return if (itemsCount == 0 || selectedItemsCount == 0) 0.0f
        else selectedItemsCount.toFloat() / itemsCount
    }
}