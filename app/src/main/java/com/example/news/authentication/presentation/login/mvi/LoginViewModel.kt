package com.example.news.authentication.presentation.login.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.authentication.domain.AuthResult
import com.example.news.authentication.domain.usecase.LoginUseCase
import com.example.news.authentication.domain.usecase.ValidateEmailUseCase
import com.example.news.authentication.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _loginSuccessfully = MutableSharedFlow<Boolean>(replay = 0)
    val loginSuccessfully = _loginSuccessfully.asSharedFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> updateState(email = intent.email)
            is LoginIntent.PasswordChanged -> updateState(password = intent.password)
            is LoginIntent.VisibilityChanged -> updateState(isPasswordVisible = intent.isVisible)
            is LoginIntent.RememberMeChanged -> updateState(isRememberMeChecked = intent.isChecked)
            LoginIntent.LoginClicked -> login()
        }
    }

    private fun updateState(
        email: String? = null,
        password: String? = null,
        isPasswordVisible: Boolean? = null,
        isRememberMeChecked: Boolean? = null
    ) {
        _loginState.value = _loginState.value.copy(
            email = email ?: _loginState.value.email,
            password = password ?: _loginState.value.password,
            isPasswordVisible = isPasswordVisible ?: _loginState.value.isPasswordVisible,
            isRememberMeChecked = isRememberMeChecked ?: _loginState.value.isRememberMeChecked,
            emailErrorMessage = if (email != null) validateEmailUseCase(email) else _loginState.value.emailErrorMessage,
            passwordErrorMessage = if (password != null) validatePasswordUseCase(password) else _loginState.value.passwordErrorMessage
        )
    }

    private fun login() {
        _loginState.value = _loginState.value.copy(
            emailErrorMessage = validateEmailUseCase(_loginState.value.email),
            passwordErrorMessage = validatePasswordUseCase(_loginState.value.password)
        )

        if (!(_loginState.value.emailErrorMessage.isNullOrBlank() && _loginState.value.passwordErrorMessage.isNullOrBlank())) return

        viewModelScope.launch {
            loginUseCase(_loginState.value.email, _loginState.value.password).collect { result ->
                _loginState.value = when (result) {
                    is AuthResult.Error -> _loginState.value.copy(
                        errorMessage = result.message,
                        isLoading = false
                    )

                    is AuthResult.Loading -> _loginState.value.copy(isLoading = true, errorMessage = null)
                    is AuthResult.Success -> {
                        _loginSuccessfully.emit(true)
                        _loginState.value.copy(isLoading = false)
                    }
                }
            }
        }
    }

}
