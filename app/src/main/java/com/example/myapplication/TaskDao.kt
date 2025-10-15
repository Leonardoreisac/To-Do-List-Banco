package com.example.myapplication

import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun getAll(): List<Task>

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}
