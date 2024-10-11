package com.example.news.authentication.domain.repository

import com.example.news.authentication.domain.AuthResult
import com.example.news.authentication.domain.model.User
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
     fun login(email: String, password: String): Flow<AuthResult<User>>
    fun signup(username: String,email: String, password: String): Flow<AuthResult<User>>
}