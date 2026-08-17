package com.example.shoppingapp.presentation.dialog_window

sealed class DialogEvent {
    data class OnTextChange(
        val text: String
    ): DialogEvent()
    object OnCancel : DialogEvent()
    object OnConfirm: DialogEvent()
}