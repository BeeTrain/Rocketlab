package io.rocketlab.service.auth.impl

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import io.rocketlab.service.auth.AuthService
import io.rocketlab.service.auth.exception.AuthServerTimeoutException
import io.rocketlab.service.auth.exception.InvalidCredentialsException
import io.rocketlab.service.auth.exception.UserNotFoundException
import io.rocketlab.service.auth.mapper.UserMapper
import io.rocketlab.service.auth.model.Credentials
import io.rocketlab.service.auth.model.User
import io.rocketlab.utils.extension.catchError
import java.util.Timer
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

private const val DEFAULT_TIMEOUT = 30L

class ProdAuthService(
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val userMapper: UserMapper
) : AuthService {

    private val firebaseUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override val user: User
        get() = userMapper.map(firebaseUser)

    override val isLogged: Boolean
        get() = firebaseUser != null

    override fun getGoogleSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    override fun registerUser(
        credentials: Credentials,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        catchError(
            createUserWithEmailAndPassword(credentials, onSuccess, onFailure),
            onFailure
        )
    }

    override fun signInWithGoogleSign(
        account: GoogleSignInAccount,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        catchError(
            signInWithGoogleCredentials(credential, onSuccess, onFailure),
            onFailure
        )
    }

    override fun signInWithCredentials(
        credentials: Credentials,
        onSuccess: ((AuthResult) -> Unit),
        onFailure: ((Exception) -> Unit)
    ) {
        catchError(
            signInWithEmailAndPassword(credentials, onSuccess, onFailure),
            onFailure
        )
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    private fun signInWithEmailAndPassword(
        credentials: Credentials,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ): () -> Unit {
        return {
            var isActive = true

            firebaseAuth.signInWithEmailAndPassword(credentials.email, credentials.password)
                .addOnSuccessListener {
                    if (isActive) {
                        onSuccess.invoke(it)
                        isActive = false
                    }
                }
                .addOnFailureListener {
                    if (isActive) {
                        onFailure.invoke(mapError(it))
                        isActive = false
                    }
                }

            Timer().schedule(TimeUnit.SECONDS.toMillis(DEFAULT_TIMEOUT)) {
                if (isActive) {
                    onFailure.invoke(AuthServerTimeoutException())
                    isActive = false
                }
            }
        }
    }

    private fun createUserWithEmailAndPassword(
        credentials: Credentials,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ): () -> Unit {
        return {
            var isActive = true

            firebaseAuth.createUserWithEmailAndPassword(credentials.email, credentials.password)
                .addOnSuccessListener {
                    if (isActive) {
                        onSuccess.invoke(it)
                        isActive = false
                    }
                }
                .addOnFailureListener {
                    if (isActive) {
                        onFailure.invoke(mapError(it))
                        isActive = false
                    }
                }

            Timer().schedule(TimeUnit.SECONDS.toMillis(DEFAULT_TIMEOUT)) {
                if (isActive) {
                    onFailure.invoke(AuthServerTimeoutException())
                    isActive = false
                }
            }
        }
    }

    private fun signInWithGoogleCredentials(
        credential: AuthCredential,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ): () -> Unit {
        return {
            var isActive = true

            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    if (isActive) {
                        onSuccess.invoke(it)
                        isActive = false
                    }
                }
                .addOnFailureListener {
                    if (isActive) {
                        onFailure.invoke(mapError(it))
                        isActive = false
                    }
                }

            Timer().schedule(TimeUnit.SECONDS.toMillis(DEFAULT_TIMEOUT)) {
                if (isActive) {
                    onFailure.invoke(AuthServerTimeoutException())
                    isActive = false
                }
            }
        }
    }

    private fun mapError(exception: Exception): Exception {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> InvalidCredentialsException(exception)
            is FirebaseAuthInvalidUserException -> UserNotFoundException(exception)
            else -> exception
        }
    }
}