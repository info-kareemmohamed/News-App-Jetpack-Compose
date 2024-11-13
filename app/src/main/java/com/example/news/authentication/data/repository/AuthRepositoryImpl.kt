package com.example.news.authentication.data.repository

import com.example.news.authentication.domain.AuthResult
import com.example.news.authentication.domain.model.User
import com.example.news.authentication.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun login(email: String, password: String): Flow<AuthResult<User>> = flow {
        emit(AuthResult.Loading())
        val uid = firebaseAuth.signInWithEmailAndPassword(email, password).await().user?.uid
        val user = getUserFromFirestore(uid.toString())
        emit(AuthResult.Success(user))
    }.catch { emit(AuthResult.Error(it.message ?: "An unknown error occurred")) }


    override fun signup(username: String, email: String, password: String): Flow<AuthResult<User>> =
        flow {
            emit(AuthResult.Loading())
            val uid = firebaseAuth.createUserWithEmailAndPassword(email, password).await().user?.uid
                ?: throw Exception("UID is null")
            val user = User(uid, email, username)
            saveUserToFirestore(user, uid)
            emit(AuthResult.Success(user))
        }.catch { emit(AuthResult.Error(it.message ?: "An unknown error occurred")) }


    override fun forgotPassword(email: String): Flow<AuthResult<String>> = flow {
        emit(AuthResult.Loading())
        firebaseAuth.sendPasswordResetEmail(email).await()
        emit(AuthResult.Success("Password reset email sent successfully"))
    }.catch { emit(AuthResult.Error(it.message ?: "An unknown error occurred")) }


    override fun googleSignIn(credential: AuthCredential): Flow<AuthResult<User>> = flow {
        emit(AuthResult.Loading())
        val user = firebaseAuth.signInWithCredential(credential).await().user
            ?: throw Exception("User is null")
        val existingUser = getUserFromFirestore(user.uid) ?: User(
           id =  user.uid,
           email =  user.email.orEmpty(),
           name =  user.displayName.orEmpty()
        ).also {
            saveUserToFirestore(it, user.uid)
        }
        emit(AuthResult.Success(existingUser))
    }.catch { emit(AuthResult.Error(it.message ?: "An unknown error occurred")) }


    override suspend fun getSignedInUser(): User? {
        val uid = firebaseAuth.currentUser?.uid ?: return null
        return getUserFromFirestore(uid)
    }

    override fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun logout() {
        firebaseAuth.signOut()
    }


    private suspend fun getUserFromFirestore(uid: String): User? {
        return firestore.collection("users").document(uid).get().await().toObject(User::class.java)
    }


    private suspend fun saveUserToFirestore(user: User, uid: String) {
        firestore.collection("users").document(uid).set(user).await()
    }


}
