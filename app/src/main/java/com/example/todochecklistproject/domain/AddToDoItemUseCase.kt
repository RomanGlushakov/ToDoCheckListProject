package com.example.todochecklistproject.domain

class AddToDoItemUseCase (private val repository: ToDoDoListRepository) {
    suspend fun addToDoItem(todoItem: TodoItem) {
        repository.addToDoItem(todoItem)
    }
}