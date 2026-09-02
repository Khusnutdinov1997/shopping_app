package com.example.shoppingapp.presentation.shopping_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.theme.GrayLight
import com.example.shoppingapp.ui.theme.GrayLightSoft
import com.example.shoppingapp.utils.UIEvent

@Composable
fun ShoppingListScreen(
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
){

    val itemList = shoppingListViewModel.list.collectAsState(emptyList())

    LaunchedEffect(true) {
        shoppingListViewModel.uiEvent.collect { uIEvent ->
            when(uIEvent){
                is UIEvent.OnNavigate -> {
                    onNavigate(uIEvent.route)
                }
                else -> {}
            }
        }

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayLightSoft),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        items(itemList.value){ item ->
            UiShoppingListItem(
                shoppingListItem = item
            ) {
                event ->
                shoppingListViewModel.onEvent(event)
            }
        }
    }
}