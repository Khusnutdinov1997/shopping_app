package com.example.shoppingapp.data.task_item

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskItem: TaskItem)

    @Query("SELECT * FROM tasks WHERE idList = :idList")
    fun getTasksByListId(idList: Int): Flow<List<TaskItem>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskItem?

    @Delete
    suspend fun deleteTask(taskItem: TaskItem)
}