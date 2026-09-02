package com.example.shoppingapp.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shoppingapp.presentation.about_screen.AboutScreen
import com.example.shoppingapp.presentation.note_screen.NoteListScreen
import com.example.shoppingapp.presentation.settings.SettingsScreen
import com.example.shoppingapp.presentation.shopping_list_screen.ShoppingListScreen
import com.example.shoppingapp.utils.Routes

@Composable
fun NavGraph(
    navController: NavHostController,
    onNavigate: (String) -> Unit
) {
    NavHost(
        startDestination = Routes.SHOPPING_LIST_SCREEN,
        navController = navController
    ){
        composable(route = Routes.SHOPPING_LIST_SCREEN){
            ShoppingListScreen{ route ->
                onNavigate(route)
            }
        }
        composable(Routes.ABOUT_SCREEN){
            AboutScreen()
        }
        composable(Routes.NOTE_LIST_SCREEN){
            NoteListScreen()
        }
        composable(Routes.SETTINGS_SCREEN){
            SettingsScreen()
        }

    }
}