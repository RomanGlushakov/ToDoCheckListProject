package com.example.todochecklistproject.domain

class DeleteToDoItemUseCase (private val repository: ToDoDoListRepository) {

    suspend fun deleteToDoItem(todoItem: TodoItem) {
            repository.deleteToDoItem(todoItem)
    }
}