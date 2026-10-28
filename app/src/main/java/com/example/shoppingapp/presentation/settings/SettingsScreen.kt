package com.example.shoppingapp.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingViewModel = hiltViewModel()
){
    val colorList = viewModel.colorList.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .systemBarsPadding()
    ) {
        Text(
            text = "Title Color",
            fontSize = 16.sp
        )
        Text(
            text = "select a title color",
            fontSize = 12.sp
        )
        LazyRow(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
        ) {
            items(colorList){ colorItem ->
                UiColorItem(
                    colorItem,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}