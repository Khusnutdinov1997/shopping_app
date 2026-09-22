package com.example.shoppingapp.presentation.main_Screen.component

import com.example.shopping.R
import com.example.shoppingapp.utils.Routes

sealed class BottomNavItem(
    val name: String,
    val icon: Int,
    val route: String
) {
    object ListItem: BottomNavItem(
        name = "Список задач",
        icon = R.drawable.book,
        route = Routes.SHOPPING_LIST_SCREEN
    )
    object NoteItem: BottomNavItem(
        name = "Заметки",
        icon = R.drawable.note,
        route = Routes.NOTE_LIST_SCREEN
    )
    object AboutItem: BottomNavItem(
        name = "Общее",
        icon = R.drawable.nfo,
        route = Routes.ABOUT_SCREEN
    )
    object SettingItem: BottomNavItem(
        name = "Настройки",
        icon = R.drawable.settings,
        route = Routes.SETTINGS_SCREEN
    )
}