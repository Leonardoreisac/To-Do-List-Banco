package com.example.myapplication.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val usernameInput: String = "",
    val passwordInput: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun onUsernameChange(newUsername: String) {
        _uiState.update { it.copy(usernameInput = newUsername) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(passwordInput = newPassword) }
    }

    fun onLoginClick() {
        _uiState.update { it.copy(isLoading = true) }

        _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
    }

    fun onNavigationHandled() {
        _uiState.update { it.copy(loginSuccess = false) }
    }
}