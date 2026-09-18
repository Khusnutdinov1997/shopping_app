package com.example.shoppingapp.presentation.main_Screen

sealed class MainScreenEvent {
    data class Navigate(
        val route: String
    ): MainScreenEvent()

    data class NavigateMain(
        val route: String
    ): MainScreenEvent()

    object OnShowDialog: MainScreenEvent()

    object OnItemSave: MainScreenEvent()
}