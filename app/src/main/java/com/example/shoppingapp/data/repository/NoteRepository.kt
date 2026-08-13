package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.note_item.NoteItem
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(noteItem: NoteItem)
    suspend fun getNoteById(id: Int): NoteItem?
    fun getAllNotes(): Flow<List<NoteItem>>
    suspend fun deleteNote(noteItem: NoteItem)
}