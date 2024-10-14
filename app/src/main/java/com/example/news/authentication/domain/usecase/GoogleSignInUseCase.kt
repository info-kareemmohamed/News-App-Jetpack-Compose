package com.example.news.authentication.domain.usecase

import com.example.news.authentication.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(credential: AuthCredential) = authRepository.googleSignIn(credential)

}