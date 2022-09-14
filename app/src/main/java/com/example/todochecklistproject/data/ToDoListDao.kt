package com.example.todochecklistproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ToDoListDao {

    @Query("SELECT * FROM item_db")
    fun getToDoList(): LiveData<List<ToDoItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDoItem(item: ToDoItemDbModel)

    @Query("DELETE FROM item_db WHERE id=:itemId")
    suspend fun deleteItem(itemId: Int)

    @Query("SELECT * FROM item_db WHERE id=:itemId LIMIT 1")
    suspend fun getToDoItem(itemId: Int): ToDoItemDbModel

}