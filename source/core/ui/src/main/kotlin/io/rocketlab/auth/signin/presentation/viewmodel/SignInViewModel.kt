package io.rocketlab.auth.signin.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.auth.signin.presentation.model.SignInErrorState
import io.rocketlab.auth.signin.presentation.model.SignInScreenState
import io.rocketlab.auth.signin.presentation.model.asContentOrNull
import io.rocketlab.service.auth.AuthService
import io.rocketlab.service.auth.exception.AuthServerTimeoutException
import io.rocketlab.service.auth.exception.UserNotFoundException
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val authService: AuthService
) : BaseViewModel() {

    val uiState = state<SignInScreenState>(SignInScreenState.Content())
    val errorState = state<SignInErrorState>(SignInErrorState.None)

    val onErrorShowedAction = action<Unit> { onErrorShowed() }

    val updateEmailAction = action<String> { updateEmail(it) }
    val validateEmailAction = action<Unit> { validateEmail() }

    val updatePasswordAction = action(::updatePassword)
    val validatePasswordAction = action<Unit> { validatePassword() }
    val updatePasswordVisibilityAction = action<Unit> { onPasswordVisibilityChanged() }

    val loginClickedAction = action(::onLoginClicked)

    private fun onErrorShowed() {
        errorState.update { SignInErrorState.None }
    }

    private fun updateEmail(newValue: String) {
        uiState.value.asContentOrNull()?.let { content ->
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

    private fun validateEmail() {
        uiState.value.asContentOrNull()?.let { content ->
            uiState.update {
                content.copy(
                    eMail = content.eMail.copy(
                        error = content.getEmailErrorOrEmpty()
                    )
                )
            }
        }
    }

    private fun updatePassword(newValue: String) {
        uiState.value.asContentOrNull()?.let { content ->
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

    private fun validatePassword() {
        uiState.value.asContentOrNull()?.let { content ->
            uiState.update {
                content.copy(
                    password = content.password.copy(
                        error = content.getPasswordErrorOrNull()
                    )
                )
            }
        }
    }

    private fun onPasswordVisibilityChanged() {
        uiState.value.asContentOrNull()?.let { content ->
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
        validateEmail()
        validatePassword()

        val contentState = uiState.value.asContentOrNull() ?: return
        if (contentState.isFieldsValid) {
            uiState.update { SignInScreenState.Loading }
            authService.signInWithCredentials(
                contentState.credentials,
                onSuccess = { onLogged.invoke() },
                onFailure = { exception ->
                    val error = when (exception) {
                        is UserNotFoundException -> SignInErrorState.UserNotFound
                        is AuthServerTimeoutException -> SignInErrorState.TimedOut
                        else -> SignInErrorState.Unknown
                    }
                    uiState.update { contentState }
                    errorState.update { error }
                }
            )
        }
    }
}