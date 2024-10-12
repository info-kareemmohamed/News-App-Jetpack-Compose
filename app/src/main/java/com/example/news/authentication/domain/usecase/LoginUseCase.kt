package com.example.news.authentication.domain.usecase

import com.example.news.authentication.domain.AuthResult
import com.example.news.authentication.domain.model.User
import com.example.news.authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke(email: String, password: String): Flow<AuthResult<User>> =
        authRepository.login(email, password)


}
