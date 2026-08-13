package com.example.shoppingapp.data.task_item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskItem(
    val idList: Int,
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val check: Boolean
)
