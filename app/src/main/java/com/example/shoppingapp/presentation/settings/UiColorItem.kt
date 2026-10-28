package com.example.shoppingapp.presentation.settings

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UiColorItem(
    colorItem: ColorItem,
    onEvent: (SettingEvent) -> Unit
){
    IconButton(
        onClick = {onEvent(SettingEvent.OnItemSelected(colorItem.color))},
        modifier = Modifier
            .padding(start = 10.dp)
            .clip(CircleShape)
            .background(Color(parseColor(colorItem.color)))
    ) {
        if(colorItem.selected){
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}