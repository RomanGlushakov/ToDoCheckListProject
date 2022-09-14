package com.example.todochecklistproject.data

import com.example.todochecklistproject.domain.TodoItem

class ToDoListMapper {

    fun modelDbToEntity(toDoItemDbModel: ToDoItemDbModel): TodoItem {
        return TodoItem(
            name = toDoItemDbModel.name,
            id = toDoItemDbModel.id,
            enabled = toDoItemDbModel.enabled
        )
    }

    fun entityToDbModel(toDoItem: TodoItem): ToDoItemDbModel {
        return ToDoItemDbModel(
            name = toDoItem.name,
            id = toDoItem.id,
            enabled = toDoItem.enabled
        )
    }

    fun toDoModelListToEntityList(list: List<ToDoItemDbModel>): List<TodoItem> {
        return list.map {
            modelDbToEntity(it)
        }

    }


}