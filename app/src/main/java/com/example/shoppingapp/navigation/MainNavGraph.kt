package com.example.shoppingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.presentation.main_Screen.MainScreen
import com.example.shoppingapp.presentation.task_screen.TaskItemScreen
import com.example.shoppingapp.utils.Routes

@Composable
fun MainNavGraph(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.MAIN_SCREEN
    ) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navHostController = navController)
        }
        composable(Routes.TASK_ITEM_SCREEN + "/{listId}") {
            TaskItemScreen()
        }
    }
}