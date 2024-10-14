package com.example.news.authentication.presentation.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.authentication.domain.AuthResult
import com.example.news.authentication.domain.usecase.ForgotPasswordUseCase
import com.example.news.authentication.domain.usecase.ValidateEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var successMessage by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
        validateEmail()
    }

    private fun validateEmail(): Boolean {
        val validationResult = validateEmailUseCase(email)
        errorMessage = validationResult
        return validationResult == null
    }

    fun forgotPassword() {
        if (!validateEmail()) return

        isLoading = true

        viewModelScope.launch {
            forgotPasswordUseCase(email).collect { result ->
                when (result) {
                    is AuthResult.Error -> {
                        successMessage = null
                        isLoading = false
                        errorMessage = result.message
                    }
                    is AuthResult.Loading -> {
                        isLoading = true
                    }
                    is AuthResult.Success -> {
                        errorMessage = null
                        isLoading = false
                        successMessage = result.data
                    }
                }
            }
        }
    }
}
