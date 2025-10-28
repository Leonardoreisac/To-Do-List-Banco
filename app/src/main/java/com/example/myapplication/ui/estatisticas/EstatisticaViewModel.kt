package com.example.myapplication.ui.estatisticas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.HabitRepository
import com.example.myapplication.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class EstatisticasUiState(
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val totalHabits: Int = 0,
    val doneHabits: Int = 0,
    val overallProgress: Float = 0f
)

class EstatisticasViewModel(
    private val taskRepository: TaskRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {

    val uiState: StateFlow<EstatisticasUiState> = combine(
        taskRepository.getAllTasks(),
        habitRepository.getAllHabits()
    ) { tasks, habits ->
        val totalTasks = tasks.size
        val completedTasks = tasks.count { it.isDone }

        val totalHabits = habits.size
        val doneHabits = habits.count { it.isDone }

        val totalItems = totalTasks + totalHabits
        val completedItems = completedTasks + doneHabits

        val progress = if (totalItems > 0) completedItems.toFloat() / totalItems.toFloat() else 0f

        EstatisticasUiState(
            totalTasks = totalTasks,
            completedTasks = completedTasks,
            totalHabits = totalHabits,
            doneHabits = doneHabits,
            overallProgress = progress
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EstatisticasUiState()
    )

}