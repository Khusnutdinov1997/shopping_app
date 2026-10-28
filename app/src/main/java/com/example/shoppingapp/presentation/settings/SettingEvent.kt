package com.example.shoppingapp.presentation.settings

sealed class SettingEvent {
    data class OnItemSelected(
        val color: String
    ): SettingEvent()
}