package io.rocketlab.auth.signin.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.auth.signin.presentation.model.SignInState
import io.rocketlab.service.auth.AuthService
import io.rocketlab.service.auth.exception.SignInTimeoutException
import io.rocketlab.service.auth.exception.UserNotFoundException
import io.rocketlab.service.auth.model.Credentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val authService: AuthService
) : BaseViewModel() {

    val uiState = MutableStateFlow<SignInState>(SignInState.Content())

    val updateEmailAction = action<String> { updateEmail(it) }
    val updatePasswordAction = action(::updatePassword)
    val updatePasswordVisibilityAction = action<Unit> { onPasswordVisibilityChanged() }
    val loginClickedAction = action(::onLoginClicked)

    private fun updateEmail(newValue: String) {
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

    private fun updatePassword(newValue: String) {
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

    private fun onPasswordVisibilityChanged() {
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

    private fun onLoginClicked(onLogged: () -> Unit) {
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