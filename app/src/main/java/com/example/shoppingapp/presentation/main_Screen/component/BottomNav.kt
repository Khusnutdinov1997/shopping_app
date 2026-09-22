package com.example.shoppingapp.presentation.main_Screen.component

import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.ui.theme.BlueLight
import com.example.shoppingapp.ui.theme.GrayLight

@Composable
fun BottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit
){
    val listItems = listOf(
        BottomNavItem.ListItem,
        BottomNavItem.NoteItem,
        BottomNavItem.AboutItem,
        BottomNavItem.SettingItem
    )
    BottomNavigation(
        modifier = Modifier.height(70.dp),
        backgroundColor = Color.White
    ) {
        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {onNavigate(item.route)},
                icon = {Icon(painterResource(item.icon), contentDescription = null)},
                label = { Text(text = item.name)},
                selectedContentColor = BlueLight,
                unselectedContentColor = GrayLight,
                alwaysShowLabel = false
            )
        }
    }
}