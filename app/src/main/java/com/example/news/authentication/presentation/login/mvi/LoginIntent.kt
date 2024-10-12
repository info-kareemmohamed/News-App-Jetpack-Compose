package com.example.news.authentication.presentation.login.mvi

sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data class RememberMeChanged(val isChecked: Boolean) : LoginIntent()
    data class VisibilityChanged(val isVisible: Boolean) : LoginIntent()
    object LoginClicked : LoginIntent()
}