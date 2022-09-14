package com.example.todochecklistproject.domain

import androidx.lifecycle.LiveData

interface ToDoDoListRepository {

    suspend fun deleteToDoItem(todoItem: TodoItem)

    suspend fun editToDoItem(todoItem: TodoItem)

    fun getItemList(): LiveData<List<TodoItem>>

    suspend fun getToDoItem(itemId: Int): TodoItem

    suspend fun addToDoItem(todoItem: TodoItem)

}