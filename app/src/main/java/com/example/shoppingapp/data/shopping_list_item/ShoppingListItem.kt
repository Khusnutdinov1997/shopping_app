package com.example.shoppingapp.data.shopping_list_item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingListItem(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val time: String,
    val allItemsCount: Int,
    val allSelectedItemsCount: Int
)
