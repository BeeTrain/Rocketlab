package io.rocketlab.service.auth

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.rocketlab.service.auth.impl.ProdAuthService
import io.rocketlab.service.auth.mapper.UserMapper
import io.rocketlab.service.auth.model.Credentials
import io.rocketlab.service.auth.model.User
import io.rocketlab.utils.system.config.Environment

interface AuthService {

    object Provider {

        fun provide(
            firebaseAuth: FirebaseAuth,
            googleSignInClient: GoogleSignInClient,
            userMapper: UserMapper
        ): AuthService {
            return ProdAuthService(firebaseAuth, googleSignInClient, userMapper)
        }
    }

    val user: User

    val isLogged: Boolean

    fun getGoogleSignInIntent(): Intent

    fun registerUser(
        credentials: Credentials,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun signInWithGoogleSign(
        account: GoogleSignInAccount,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun signInWithCredentials(
        credentials: Credentials,
        onSuccess: ((AuthResult) -> Unit),
        onFailure: ((Exception) -> Unit) = {}
    )

    fun signOut()
}