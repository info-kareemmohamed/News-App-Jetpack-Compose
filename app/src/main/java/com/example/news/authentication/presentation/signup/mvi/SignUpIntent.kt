package com.example.news.authentication.presentation.signup.mvi

sealed class SignUpIntent {
    data class UsernameChanged(val username: String) : SignUpIntent()
    data class EmailChanged(val email: String) : SignUpIntent()
    data class PasswordChanged(val password: String) : SignUpIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpIntent()
    data class PasswordVisibilityChanged(val isVisible: Boolean) : SignUpIntent()
    data class ConfirmPasswordVisibilityChanged(val isVisible: Boolean) : SignUpIntent()
    object Submit : SignUpIntent()

}