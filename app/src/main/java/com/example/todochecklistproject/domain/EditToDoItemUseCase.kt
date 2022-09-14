package com.example.todochecklistproject.domain

class EditToDoItemUseCase (private val repository: ToDoDoListRepository) {

    suspend fun editToDoItem(todoItem: TodoItem) {
        repository.editToDoItem(todoItem)
    }
}