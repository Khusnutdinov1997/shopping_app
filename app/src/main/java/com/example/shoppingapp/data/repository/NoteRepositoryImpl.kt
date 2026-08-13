package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.note_item.NoteDao
import com.example.shoppingapp.data.note_item.NoteItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun insertNote(noteItem: NoteItem) {
        noteDao.insertNote(noteItem)
    }

    override suspend fun getNoteById(id: Int): NoteItem? {
        return noteDao.getNoteById(id)
    }

    override fun getAllNotes(): Flow<List<NoteItem>> {
        return noteDao.getAllNotes()
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        noteDao.deleteNote(noteItem)
    }

}