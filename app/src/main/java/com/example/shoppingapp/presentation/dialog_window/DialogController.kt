package com.example.shoppingapp.presentation.dialog_window

import androidx.compose.runtime.MutableState

interface DialogController {
    val dialogTitle: MutableState<String>
    val editableText: MutableState<String>
    val openDialog: MutableState<Boolean>
    val showEditableText: MutableState<Boolean>

    fun onDialogEvent(event: DialogEvent)
}