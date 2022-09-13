package io.rocketlab.service.auth

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.rocketlab.service.auth.exception.SignInTimeoutException
import io.rocketlab.service.auth.model.Credentials
import io.rocketlab.utils.extension.catchError
import java.util.Timer
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

private const val DEFAULT_SIGN_IN_TIMEOUT = 30L

class AuthService(
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) {

    private val firebaseUser: FirebaseUser?
        get() = null

    val isLogged: Boolean
        get() = firebaseUser != null

    fun getGoogleSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    fun signInWithCredentials(
        credentials: Credentials,
        onSuccess: ((Task<AuthResult>) -> Unit),
        onFailure: ((Exception) -> Unit) = {}
    ) {
        catchError(
            signInWithEmailAndPassword(credentials, onSuccess, onFailure),
            onFailure
        )
    }

    private fun signInWithEmailAndPassword(
        credentials: Credentials,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (Exception) -> Unit
    ): () -> Unit {
        return {
            var isActive = true

            firebaseAuth.signInWithEmailAndPassword(credentials.email, credentials.password)
                .addOnCompleteListener { if (isActive) onSuccess.invoke(it) }
                .addOnFailureListener { if (isActive) onFailure.invoke(it) }

            Timer().schedule(TimeUnit.SECONDS.toMillis(DEFAULT_SIGN_IN_TIMEOUT)) {
                isActive = false
                onFailure.invoke(SignInTimeoutException())
            }
        }
    }
}