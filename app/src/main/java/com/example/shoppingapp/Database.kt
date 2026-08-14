package com.example.shoppingapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingapp.data.note_item.NoteDao
import com.example.shoppingapp.data.note_item.NoteItem
import com.example.shoppingapp.data.shopping_list_item.ShoppingListDao
import com.example.shoppingapp.data.shopping_list_item.ShoppingListItem
import com.example.shoppingapp.data.task_item.TaskDao
import com.example.shoppingapp.data.task_item.TaskItem

@Database(
    entities = [
        TaskItem::class,
        NoteItem::class,
        ShoppingListItem::class
    ], version = 1
)
abstract class Database : RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val noteDao: NoteDao

    abstract val shoppingListDao: ShoppingListDao
}