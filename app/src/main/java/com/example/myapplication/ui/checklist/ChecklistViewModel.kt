package com.example.myapplication.ui.checklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Habit
import com.example.myapplication.data.repository.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChecklistViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

    val habits: StateFlow<List<Habit>> = habitRepository.getAllHabits()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun toggleHabitCompletion(habit: Habit) {
        val updatedHabit = habit.copy(isDone = !habit.isDone)
        viewModelScope.launch {
            habitRepository.updateHabit(updatedHabit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            habitRepository.deleteHabit(habit)
        }
    }

    fun addHabit(title: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            val newHabit = Habit(title = title)
            habitRepository.insertHabit(newHabit)
        }
    }
}