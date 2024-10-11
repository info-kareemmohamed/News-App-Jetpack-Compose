package com.example.news.authentication.data.repository

import com.example.news.authentication.domain.AuthResult
import com.example.news.authentication.domain.model.User
import com.example.news.authentication.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun login(email: String, password: String): Flow<AuthResult<User>> = flow {
        emit(AuthResult.Loading()) // Emit loading state
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        val uid = authResult.user?.uid

        // Retrieve the user document from Firestore
        val db = FirebaseFirestore.getInstance()
        val documentSnapshot = db.collection("users")
            .document(uid.toString())
            .get()
            .await()

        val user = if (documentSnapshot.exists()) {
            documentSnapshot.toObject(User::class.java)
        } else {
            null
        }

        emit(AuthResult.Success(user))
    }.catch { e ->
        emit(AuthResult.Error(e.message ?: "An unknown error occurred")) // Handle exceptions
    }


    override fun signup(username: String, email: String, password: String): Flow<AuthResult<User>> = flow {
        emit(AuthResult.Loading()) // Emit loading state

        try {
            // Create user in Firebase Authentication
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: throw Exception("UID is null")

            // Create a user document in Firestore
            val db = FirebaseFirestore.getInstance()

            //Create a User object
            val user = User(uid, email, username)

            // Save the user data in the "users" collection with UID as the document ID
            db.collection("users")
                .document(uid)
                .set(user)
                .await() // Wait for the Firestore operation to complete

            // Emit success with the created user object
            emit(AuthResult.Success(user))
        } catch (e: Exception) {
            // Emit error in case of failure
            emit(AuthResult.Error(e.message ?: "An unknown error occurred"))
        }
    }

}