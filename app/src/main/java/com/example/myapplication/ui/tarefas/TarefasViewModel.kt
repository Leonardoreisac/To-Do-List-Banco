package com.example.myapplication.ui.tarefas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Task
import com.example.myapplication.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TarefasViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val tasks: StateFlow<List<Task>> = taskRepository.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTask(title: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            val newTask = Task(title = title)
            taskRepository.insertTask(newTask)
        }
    }

    fun toggleTaskCompletion(task: Task) {
        val updatedTask = task.copy(isDone = !task.isDone)
        viewModelScope.launch {
            taskRepository.updateTask(updatedTask)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}