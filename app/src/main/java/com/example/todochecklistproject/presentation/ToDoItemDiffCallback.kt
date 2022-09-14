package com.example.todochecklistproject.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.todochecklistproject.domain.TodoItem

class ToDoItemDiffCallback: DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }
}