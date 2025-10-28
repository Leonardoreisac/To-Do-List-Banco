package com.example.myapplication.ui.tarefas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.TaskRepository

class TaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TarefasViewModel::class.java)) {
            return TarefasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}