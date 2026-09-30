package com.example.shoppingapp.presentation.note_screen

import com.example.shoppingapp.data.note_item.NoteItem

sealed class NoteListEvent {
    data class onShowDeleteDialog(
        val item: NoteItem
    ): NoteListEvent()
    data class onItemClick(
        val route: String
    ): NoteListEvent()
    object undoneDeleteItem: NoteListEvent()
    data class onTextSearchChange(
        val text: String
    ): NoteListEvent()
}