package com.example.news.authentication.domain.usecase

import com.example.news.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String) = authRepository.forgotPassword(email)

}