package com.example.myapplication.ui.checklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.HabitRepository

class ChecklistViewModelFactory(
    private val repository: HabitRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChecklistViewModel::class.java)) {
            return ChecklistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}