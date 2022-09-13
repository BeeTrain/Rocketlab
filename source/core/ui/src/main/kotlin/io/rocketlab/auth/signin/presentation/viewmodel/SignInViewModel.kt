package io.rocketlab.auth.signin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import io.rocketlab.auth.signin.presentation.model.SignInState
import io.rocketlab.service.auth.AuthService
import io.rocketlab.service.auth.exception.SignInTimeoutException
import io.rocketlab.service.auth.exception.UserNotFoundException
import io.rocketlab.service.auth.model.Credentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val authService: AuthService
) : ViewModel() {

    val uiState = MutableStateFlow<SignInState>(SignInState.Content())

    fun updateEmail(newValue: String) {
        (uiState.value as? SignInState.Content)?.let { content ->
            uiState.update {
                content.copy(
                    eMail = content.eMail.copy(
                        value = newValue,
                        error = ""
                    )
                )
            }
        }
    }

    fun updatePassword(newValue: String) {
        (uiState.value as? SignInState.Content)?.let { content ->
            uiState.update {
                content.copy(
                    password = content.password.copy(
                        value = newValue,
                        error = ""
                    )
                )
            }
        }
    }

    fun onPasswordVisibilityClicked() {
        (uiState.value as? SignInState.Content)?.let { content ->
            val isPasswordVisible = content.password.isVisible
            uiState.update {
                content.copy(
                    password = content.password.copy(
                        isVisible = isPasswordVisible.not()
                    )
                )
            }
        }
    }

    fun onLoginClicked(onLogged: () -> Unit) {
        (uiState.value as? SignInState.Content)?.let { content ->
            content.validate(
                { credentials -> signIn(credentials, onLogged) },
                { errorContent -> uiState.update { errorContent } }
            )
        }
    }

    private fun signIn(
        credentials: Credentials,
        onLogged: (() -> Unit)
    ) {
        val previousState = uiState.value
        uiState.update { SignInState.Loading }

        authService.signInWithCredentials(
            credentials,
            onSuccess = { onLogged.invoke() },
            onFailure = { exception ->
                val error = when (exception) {
                    is UserNotFoundException -> SignInState.Error.UserNotFound
                    is SignInTimeoutException -> SignInState.Error.TimedOut
                    else -> SignInState.Error.Unknown
                }
                val stateWithError = if (previousState is SignInState.Content) {
                    previousState.copy(error = error)
                } else {
                    SignInState.Content(error = error)
                }
                uiState.update { stateWithError }
            }
        )
    }
}