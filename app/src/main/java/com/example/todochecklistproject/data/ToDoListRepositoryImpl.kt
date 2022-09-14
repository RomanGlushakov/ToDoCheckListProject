package com.example.todochecklistproject.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.todochecklistproject.domain.ToDoDoListRepository
import com.example.todochecklistproject.domain.TodoItem

class ToDoListRepositoryImpl(
    application: Application
): ToDoDoListRepository {


    private val toDoItemDao = AppDataBase.getInstance(application).toDoItemDao()
    private val mapper = ToDoListMapper()




    override suspend fun deleteToDoItem(todoItem: TodoItem) {
        toDoItemDao.deleteItem(todoItem.id)
    }

    override suspend fun editToDoItem(todoItem: TodoItem) {
        toDoItemDao.addToDoItem(mapper.entityToDbModel(todoItem))
    }

    override suspend fun addToDoItem(todoItem: TodoItem) {
        toDoItemDao.addToDoItem(mapper.entityToDbModel(todoItem))
    }

    override fun getItemList(): LiveData<List<TodoItem>> = Transformations.
    map(toDoItemDao.getToDoList()) {
        mapper.toDoModelListToEntityList(it)
    }

    override suspend fun getToDoItem(itemId: Int): TodoItem {
        return  mapper.modelDbToEntity(toDoItemDao.getToDoItem(itemId))
    }



    }