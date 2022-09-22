package io.rocketlab.service.auth

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.rocketlab.service.auth.impl.DebugAuthService
import io.rocketlab.service.auth.impl.ProdAuthService
import io.rocketlab.service.auth.model.Credentials
import io.rocketlab.utils.system.config.Environment

interface AuthService {

    class Provider(
        private val firebaseAuth: FirebaseAuth,
        private val googleSignInClient: GoogleSignInClient,
        private val environment: Environment
    ) {

        fun provide(): AuthService {
            val prodAuthService = ProdAuthService(firebaseAuth, googleSignInClient)

            return if (environment.isDebug) {
                DebugAuthService(prodAuthService)
            } else {
                prodAuthService
            }
        }
    }

    val isLogged: Boolean

    fun getGoogleSignInIntent(): Intent

    fun registerUser(
        credentials: Credentials,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun signInWithGoogleSign(
        account: GoogleSignInAccount,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun signInWithCredentials(
        credentials: Credentials,
        onSuccess: ((Task<AuthResult>) -> Unit),
        onFailure: ((Exception) -> Unit) = {}
    )
}