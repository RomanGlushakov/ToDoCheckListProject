package com.example.todochecklistproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_db")
data class ToDoItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val enabled: Boolean
)