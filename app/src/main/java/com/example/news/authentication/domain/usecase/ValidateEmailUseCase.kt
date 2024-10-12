package com.example.news.authentication.domain.usecase

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): String? {
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        return when {
            email.isBlank() -> "Email cannot be empty"
            !emailPattern.matches(email) -> "Invalid email format"
            else -> null
        }
    }
}