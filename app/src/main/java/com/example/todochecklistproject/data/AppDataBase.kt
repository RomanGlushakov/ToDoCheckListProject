package com.example.todochecklistproject.data


import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ToDoItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun toDoItemDao(): ToDoListDao

    companion object {
        private const val NAME_DB = "toDoItems.db"

        var INSTANCE: AppDataBase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): AppDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application, AppDataBase::class.java, NAME_DB
                ).build()
                INSTANCE = db
                return db
            }
        }

    }
}