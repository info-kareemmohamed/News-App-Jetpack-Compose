package com.example.news.authentication.domain.usecase

import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor() {
    operator fun invoke(username: String): String? {
        val usernameRegex = "^[A-Za-z\\s]+\$".toRegex()

        return when {
            username.isBlank() -> "Username can't be empty"
            username.length < 3 -> "Username should be at least 3 characters"
            username.length > 20 -> "Username should not exceed 20 characters"
            !username.matches(usernameRegex) -> "Username should only contain letters and spaces"
            else -> null
        }
    }

}