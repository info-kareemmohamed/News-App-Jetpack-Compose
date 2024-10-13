package com.example.news.authentication.domain.usecase

import com.example.news.authentication.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(username: String, email: String, password: String) =
        authRepository.signup(username, email, password)

}