package io.rocketlab.service.auth.impl

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import io.rocketlab.service.auth.AuthService
import io.rocketlab.service.auth.exception.AuthServerTimeoutException
import io.rocketlab.service.auth.model.Credentials
import io.rocketlab.service.auth.model.User
import io.rocketlab.utils.extension.catchError
import java.util.Timer
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

private const val DEFAULT_TIMEOUT = 30L

class ProdAuthService(
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) : AuthService {

    private val firebaseUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override val user: User?
        get() = firebaseUser?.let {
            User(
                name = it.displayName.orEmpty(),
                eMail = it.email.orEmpty(),
                photoUrl = it.photoUrl?.toString()
            )
        }

    override val isLogged: Boolean
        get() = firebaseUser != null

    override fun getGoogleSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    override fun registerUser(
        credentials: Credentials,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        catchError(
            createUserWithEmailAndPassword(credentials, onSuccess, onFailure),
            onFailure
        )
    }

    override fun signInWithGoogleSign(
        account: GoogleSignInAccount,
        onSuccess: (Task<AuthResult>) -> Unit,
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
        onSuccess: ((Task<AuthResult>) -> Unit),
        onFailure: ((Exception) -> Unit)
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

            Timer().schedule(TimeUnit.SECONDS.toMillis(DEFAULT_TIMEOUT)) {
                isActive = false
                onFailure.invoke(AuthServerTimeoutException())
            }
        }
    }

    private fun createUserWithEmailAndPassword(
        credentials: Credentials,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (Exception) -> Unit
    ): () -> Unit {
        return {
            var isActive = true

            firebaseAuth.createUserWithEmailAndPassword(credentials.email, credentials.password)
                .addOnCompleteListener { if (isActive) onSuccess.invoke(it) }
                .addOnFailureListener { if (isActive) onFailure.invoke(it) }

            Timer().schedule(TimeUnit.SECONDS.toMillis(DEFAULT_TIMEOUT)) {
                isActive = false
                onFailure.invoke(AuthServerTimeoutException())
            }
        }
    }

    private fun signInWithGoogleCredentials(
        credential: AuthCredential,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (Exception) -> Unit
    ): () -> Unit {
        return {
            var isActive = true

            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { if (isActive) onSuccess.invoke(it) }
                .addOnFailureListener { if (isActive) onFailure.invoke(it) }

            Timer().schedule(TimeUnit.SECONDS.toMillis(DEFAULT_TIMEOUT)) {
                isActive = false
                onFailure.invoke(AuthServerTimeoutException())
            }
        }
    }
}