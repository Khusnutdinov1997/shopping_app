package com.example.shoppingapp.data.note_item

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteItem: NoteItem)

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteItem?

    @Query("SELECT * FROM notes ORDER BY time DESC")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Delete
    suspend fun deleteNote(noteItem: NoteItem)

}