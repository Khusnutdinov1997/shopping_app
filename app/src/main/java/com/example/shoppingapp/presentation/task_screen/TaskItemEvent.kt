package com.example.shoppingapp.presentation.task_screen

import com.example.shoppingapp.data.task_item.TaskItem

sealed class TaskItemEvent {
    data class OnTextChange(val text: String): TaskItemEvent()
    data class OnCheckedChange(val item: TaskItem): TaskItemEvent()
    data class OnDelete(val item: TaskItem): TaskItemEvent()
    object OnSaveTask: TaskItemEvent()
    data class OnShowEditDialog(val item: TaskItem): TaskItemEvent()
}