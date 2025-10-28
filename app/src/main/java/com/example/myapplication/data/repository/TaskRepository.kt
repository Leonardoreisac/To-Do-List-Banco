package com.example.myapplication.data.repository

import com.example.myapplication.data.local.Task
import com.example.myapplication.data.local.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }
}