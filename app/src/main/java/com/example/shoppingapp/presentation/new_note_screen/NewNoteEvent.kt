package com.example.shoppingapp.presentation.new_note_screen

sealed class NewNoteEvent {
    object OnSave: NewNoteEvent()
    data class OnTitleChange(
        val title: String
    ): NewNoteEvent()

    data class OnDescription(
        val description: String
    ): NewNoteEvent()

}