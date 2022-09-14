package com.example.todochecklistproject.domain

import androidx.lifecycle.LiveData

class GetItemListUseCase (private val repository: ToDoDoListRepository) {

    fun getItemList(): LiveData<List<TodoItem>> {
        return  repository.getItemList()
    }
}