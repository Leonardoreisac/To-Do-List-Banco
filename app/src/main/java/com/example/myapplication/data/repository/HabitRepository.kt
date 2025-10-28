package com.example.myapplication.data.repository

import com.example.myapplication.data.local.Habit
import com.example.myapplication.data.local.HabitDao
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
    
    fun getAllHabits(): Flow<List<Habit>> = habitDao.getAllHabits()

    suspend fun insertHabit(habit: Habit) {
        habitDao.insert(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.update(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.delete(habit)
    }
}