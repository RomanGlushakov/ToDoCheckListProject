package com.example.todochecklistproject.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todochecklistproject.data.ToDoListRepositoryImpl
import com.example.todochecklistproject.domain.AddToDoItemUseCase
import com.example.todochecklistproject.domain.EditToDoItemUseCase
import com.example.todochecklistproject.domain.GetToDoItemUseCase
import com.example.todochecklistproject.domain.TodoItem
import kotlinx.coroutines.launch

class ToDoItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ToDoListRepositoryImpl(application)

    private val getToDoItemUseCase = GetToDoItemUseCase(repository)
    private val addToDoItemUseCase = AddToDoItemUseCase(repository)
    private val editToDoItemUseCase = EditToDoItemUseCase(repository)


    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _toDoItem = MutableLiveData<TodoItem>()
    val toDoItem: LiveData<TodoItem>
        get() = _toDoItem

    private val _inputErrorName = MutableLiveData<Boolean>()
    val inputErrorName: LiveData<Boolean>
        get() = _inputErrorName


    fun getToDoItem(itemId: Int) {
        viewModelScope.launch {
            _toDoItem.value = getToDoItemUseCase.getToDoItem(itemId)
        }

    }

    fun addToDoItem(inputName: String?) {
        val name = validInputName(inputName)
        val isValid = validateInput(name)
        if (isValid) {
            viewModelScope.launch {
                val toDoItem = TodoItem(name = name, enabled = false)
                addToDoItemUseCase.addToDoItem(toDoItem)
                finishWork()
            }
        }
    }

    fun editToDoItem(inputName: String?) {
        val name = validInputName(inputName)
        val isValid = validateInput(name)
        if (isValid) {

            _toDoItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name)
                    editToDoItemUseCase.editToDoItem(item)
                }

            }
            finishWork()
        }
    }


    private fun validInputName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun validateInput(name: String): Boolean {
        var result = true
        if (name.isBlank()) {
            _inputErrorName.value = true
            result = false
        }
        return result
    }

    fun resetNameError() {
        _inputErrorName.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }


}