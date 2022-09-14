package com.example.todochecklistproject.domain

class GetToDoItemUseCase (private val repository: ToDoDoListRepository) {

    suspend fun getToDoItem(itemId: Int): TodoItem {
        return  repository.getToDoItem(itemId)
    }
}