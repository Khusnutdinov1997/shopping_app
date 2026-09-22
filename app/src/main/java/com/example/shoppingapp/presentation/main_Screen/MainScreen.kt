package com.example.shoppingapp.presentation.main_Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.presentation.main_Screen.component.BottomNav
import com.example.shoppingapp.utils.UIEvent

@Composable
fun MainScreen (
    mainViewModel: MainScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        currentRoute.let{ route ->
            mainViewModel.updateFloatingButtonVisibility(route!!)
        }
    }

    LaunchedEffect(true) {
        mainViewModel.uiEvent.collect { uIEvent ->
            when(uIEvent){
                is UIEvent.OnNavigate -> {
                    navController.navigate(uIEvent.route)
                }
                is UIEvent.OnNavigateMain -> {
                    navHostController.navigate(uIEvent.route)
                }
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Scaffold(
            bottomBar = {
                BottomNav(
                    currentRoute = currentRoute
                ) { route ->
                    navController.navigate(route){
                        popUpTo(navController.graph.startDestinationId){saveState = true}
                        launchSingleTop = true // исключает дубликаты экранов внутри стека
                        restoreState = true // восстанавливает при возвращении
                    }
                }
            }
        ) {}
    }

}