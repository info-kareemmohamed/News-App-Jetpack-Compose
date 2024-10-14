package com.example.news.authentication.presentation.login.mvi

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isRememberMeChecked: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingGoogle: Boolean = false,
    val errorMessage: String? = null,
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null
)
