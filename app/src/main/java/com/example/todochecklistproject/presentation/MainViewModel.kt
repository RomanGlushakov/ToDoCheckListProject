package com.example.todochecklistproject.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todochecklistproject.data.ToDoListRepositoryImpl
import com.example.todochecklistproject.domain.DeleteToDoItemUseCase
import com.example.todochecklistproject.domain.EditToDoItemUseCase
import com.example.todochecklistproject.domain.GetItemListUseCase
import com.example.todochecklistproject.domain.TodoItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ToDoListRepositoryImpl(application)

    private val editToDoItemUseCase = EditToDoItemUseCase(repository)
    private val getItemListUseCase = GetItemListUseCase(repository)
    private val deleteToDoItemUseCase = DeleteToDoItemUseCase(repository)

    val toDoItemList = getItemListUseCase.getItemList()

    fun deleteItem(toDoItem: TodoItem) {
        viewModelScope.launch {
            deleteToDoItemUseCase.deleteToDoItem(toDoItem)
        }

    }

    fun changeEnableState(toDoItem: TodoItem) {

            viewModelScope.launch {
                val item = toDoItem.copy(enabled = !toDoItem.enabled)
                editToDoItemUseCase.editToDoItem(item)
            }

    }







}