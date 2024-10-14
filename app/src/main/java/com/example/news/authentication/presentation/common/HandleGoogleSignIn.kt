package com.example.news.authentication.presentation.common

import android.app.Activity.RESULT_OK
import androidx.activity.result.ActivityResult
import com.example.news.core.util.Constants.FIREBASE_SERVER_CLIENT_ID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider


fun createGoogleSignInOptions(): GoogleSignInOptions {
    return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(FIREBASE_SERVER_CLIENT_ID)
        .build()
}

fun handleGoogleSignInResult(
    activityResult: ActivityResult,
    onCredentialReceived: (credential: AuthCredential) -> Unit
) {
    if (activityResult.resultCode == RESULT_OK) {
        val signedInAccount = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
        try {
            // Get the Google account information
            val googleSignInAccount = signedInAccount.getResult(ApiException::class.java)
            // Get the GoogleAuthCredential
            val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
            onCredentialReceived(authCredential)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
