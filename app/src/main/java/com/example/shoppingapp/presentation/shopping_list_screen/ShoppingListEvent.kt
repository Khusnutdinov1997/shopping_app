package com.example.shoppingapp.presentation.shopping_list_screen

import com.example.shoppingapp.data.shopping_list_item.ShoppingListItem

sealed class ShoppingListEvent {

    data class OnShowDeleteDialog(
        val shoppingListItem: ShoppingListItem
    ): ShoppingListEvent()

    data class OnShowEditDialog(
        val shoppingListItem: ShoppingListItem
    ): ShoppingListEvent()

    object OnItemSave: ShoppingListEvent()

    data class OnItemClick(
        val route: String
    ): ShoppingListEvent()
}