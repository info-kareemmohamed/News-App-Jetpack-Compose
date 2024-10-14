package com.example.news.authentication.presentation.signup.mvi

import com.example.news.authentication.presentation.login.mvi.LoginIntent
import com.google.firebase.auth.AuthCredential

sealed class SignUpIntent {
    data class UsernameChanged(val username: String) : SignUpIntent()
    data class EmailChanged(val email: String) : SignUpIntent()
    data class PasswordChanged(val password: String) : SignUpIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpIntent()
    data class PasswordVisibilityChanged(val isVisible: Boolean) : SignUpIntent()
    data class ConfirmPasswordVisibilityChanged(val isVisible: Boolean) : SignUpIntent()
    data class GoogleSignInClicked(val credential: AuthCredential) :SignUpIntent()
    object Submit : SignUpIntent()

}