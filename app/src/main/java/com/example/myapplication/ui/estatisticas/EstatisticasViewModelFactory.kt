package com.example.myapplication.ui.estatisticas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.HabitRepository
import com.example.myapplication.data.repository.TaskRepository

class EstatisticasViewModelFactory(
    private val taskRepository: TaskRepository,
    private val habitRepository: HabitRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstatisticasViewModel::class.java)) {
            return EstatisticasViewModel(taskRepository, habitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}