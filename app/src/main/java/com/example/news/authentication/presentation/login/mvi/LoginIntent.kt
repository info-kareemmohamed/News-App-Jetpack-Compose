package com.example.news.authentication.presentation.login.mvi

import com.google.firebase.auth.AuthCredential

sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data class RememberMeChanged(val isChecked: Boolean) : LoginIntent()
    data class VisibilityChanged(val isVisible: Boolean) : LoginIntent()
    data class GoogleSignInClicked(val credential: AuthCredential) : LoginIntent()
    object LoginClicked : LoginIntent()
}