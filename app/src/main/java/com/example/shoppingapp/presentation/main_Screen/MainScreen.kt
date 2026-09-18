package com.example.shoppingapp.presentation.main_Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen (
    mainViewModel: MainScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRout = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRout) {
        currentRout.let{ route ->
            mainViewModel.updateFloatingButtonVisibility(route!!)
        }
    }

}