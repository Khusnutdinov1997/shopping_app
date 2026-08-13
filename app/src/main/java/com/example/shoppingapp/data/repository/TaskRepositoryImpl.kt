package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.task_item.TaskDao
import com.example.shoppingapp.data.task_item.TaskItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun insertTask(taskItem: TaskItem) {
        taskDao.insertTask(taskItem)
    }

    override fun getTasksByListId(idList: Int): Flow<List<TaskItem>> {
        return taskDao.getTasksByListId(idList)
    }

    override suspend fun getTaskById(id: Int): TaskItem? {
        return taskDao.getTaskById(id)
    }

    override suspend fun deleteTask(taskItem: TaskItem) {
        taskDao.deleteTask(taskItem)
    }

}