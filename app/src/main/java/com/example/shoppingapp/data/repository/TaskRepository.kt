package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.task_item.TaskItem
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(taskItem: TaskItem)
    suspend fun updateTask(taskItem: TaskItem)
    fun getTasksByListId(idList: Int): Flow<List<TaskItem>>
    suspend fun getTaskById(id: Int): TaskItem?
    suspend fun deleteTask(taskItem: TaskItem)
}