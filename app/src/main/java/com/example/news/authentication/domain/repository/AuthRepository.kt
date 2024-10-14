package com.example.news.authentication.domain.repository

import android.content.IntentSender
import com.example.news.authentication.domain.AuthResult
import com.example.news.authentication.domain.model.User
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun login(email: String, password: String): Flow<AuthResult<User>>
    fun signup(username: String, email: String, password: String): Flow<AuthResult<User>>
    fun forgotPassword(email: String): Flow<AuthResult<String>>
    fun googleSignIn(credential: AuthCredential): Flow<AuthResult<User>>
    suspend fun getSignedInUser(): User?
    fun signOut(): Flow<AuthResult<String>>

}