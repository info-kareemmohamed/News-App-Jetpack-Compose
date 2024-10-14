package com.example.news.authentication.presentation.signup.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.authentication.domain.AuthResult
import com.example.news.authentication.domain.usecase.GoogleSignInUseCase
import com.example.news.authentication.domain.usecase.SignUpUseCase
import com.example.news.authentication.domain.usecase.ValidateEmailUseCase
import com.example.news.authentication.domain.usecase.ValidatePasswordUseCase
import com.example.news.authentication.domain.usecase.ValidateUserNameUseCase
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _signUpSuccessfully = MutableSharedFlow<Boolean>(replay = 0)
    val signUpSuccessfully = _signUpSuccessfully.asSharedFlow()

    fun onIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.ConfirmPasswordChanged -> confirmPasswordChanged(intent.confirmPassword)
            is SignUpIntent.EmailChanged -> updateState(email = intent.email)
            is SignUpIntent.PasswordChanged -> updateState(password = intent.password)
            is SignUpIntent.UsernameChanged -> updateState(username = intent.username)
            is SignUpIntent.ConfirmPasswordVisibilityChanged -> updateState(
                confirmPasswordVisibility = intent.isVisible
            )

            is SignUpIntent.PasswordVisibilityChanged -> updateState(passwordVisibility = intent.isVisible)
             SignUpIntent.Submit -> submit()
            is SignUpIntent.GoogleSignInClicked -> googleSignIn(intent.credential)
        }
    }

    private fun updateState(
        email: String? = null,
        password: String? = null,
        username: String? = null,
        confirmPasswordVisibility: Boolean? = null,
        passwordVisibility: Boolean? = null
    ) {
        _signUpState.value = _signUpState.value.copy(
            email = email ?: _signUpState.value.email,
            password = password ?: _signUpState.value.password,
            username = username ?: _signUpState.value.username,
            emailErrorMessage = if (email != null) validateEmailUseCase(email) else _signUpState.value.emailErrorMessage,
            passwordErrorMessage = if (password != null) validatePasswordUseCase(password) else _signUpState.value.passwordErrorMessage,
            usernameErrorMessage = if (username != null) validateUserNameUseCase(username) else _signUpState.value.usernameErrorMessage,
            isPasswordVisible = passwordVisibility ?: _signUpState.value.isPasswordVisible,
            isConfirmPasswordVisible = confirmPasswordVisibility
                ?: _signUpState.value.isConfirmPasswordVisible
        )
    }

    private fun confirmPasswordChanged(confirmPassword: String) {
        _signUpState.value = _signUpState.value.copy(
            confirmPassword = confirmPassword,
            confirmPasswordErrorMessage = null
        )
        if (confirmPassword != _signUpState.value.password) {
            _signUpState.value = _signUpState.value.copy(
                confirmPasswordErrorMessage = "Password and confirm password do not match."
            )
        }
    }

    private fun checkAllFields(): Boolean {
        _signUpState.value.copy(
            emailErrorMessage = validateEmailUseCase(_signUpState.value.email),
            passwordErrorMessage = validatePasswordUseCase(_signUpState.value.password),
            confirmPasswordErrorMessage = validatePasswordUseCase(_signUpState.value.confirmPassword),
            usernameErrorMessage = validateUserNameUseCase(_signUpState.value.username)
        )
        val hasError = listOf(
            _signUpState.value.emailErrorMessage,
            _signUpState.value.passwordErrorMessage,
            _signUpState.value.confirmPasswordErrorMessage,
            _signUpState.value.usernameErrorMessage
        ).any { it != null }
        return hasError
    }


    private fun submit() {
        if (checkAllFields()) return
        val state = _signUpState.value
        viewModelScope.launch {
            signUpUseCase(state.username, state.email, state.password).collect { result ->
                _signUpState.value = when (result) {
                    is AuthResult.Error -> state.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )

                    is AuthResult.Loading -> state.copy(isLoading = true)
                    is AuthResult.Success -> {
                        _signUpSuccessfully.emit(true)
                        state.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun googleSignIn(credential: AuthCredential) {
        viewModelScope.launch {
            googleSignInUseCase(credential).collect { result ->
                when (result) {
                    is AuthResult.Error -> _signUpState.value.copy(
                        errorMessage = result.message,
                        isLoadingGoogle = false
                    )

                    is AuthResult.Loading -> _signUpState.value.copy(
                        isLoadingGoogle = true,
                        errorMessage = null
                    )

                    is AuthResult.Success -> {
                        _signUpSuccessfully.emit(true)
                        _signUpState.value.copy(isLoadingGoogle = false)
                    }
                }
            }
        }

    }
}
