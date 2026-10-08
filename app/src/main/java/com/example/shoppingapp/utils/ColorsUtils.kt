package com.example.shoppingapp.utils

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import com.example.shoppingapp.ui.theme.Green
import com.example.shoppingapp.ui.theme.Red
import com.example.shoppingapp.ui.theme.Yellow

object ColorsUtils {
    val colorsList = listOf(
        "#FFB388FF",
        "#FF82B1FF",
        "#FF80D8FF",
        "#FFFF80AB",
        "#FFE680C8",
        "#FF80C8FF",
        "#FFA8A8FF",
        "#FFFF80FF",
        "#FFFFFF80",
        "#FFFFD080",
        "#FF80FFA8",
        "#FFFFB080",
        "#FFFFA8FF",
        "#FFA8FF80"
    )

    fun getProgressColor(progress: Float): Color {
        return when (progress) {

            in 0.0..0.339 -> Red

            in 0.34..0.669 -> Yellow

            in 0.67..1.0 -> Green

            else -> Red
        }
    }
}