package com.example.shoppingapp.data.note_item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteItem(
    val title: String,
    val description: String,
    @PrimaryKey
    val id: Int? = null,
    val time: String
    )
