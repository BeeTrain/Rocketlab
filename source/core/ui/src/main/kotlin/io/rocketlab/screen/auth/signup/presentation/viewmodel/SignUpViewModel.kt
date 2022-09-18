package io.rocketlab.screen.auth.signup.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.screen.auth.signup.presentation.model.SignUpErrorState
import io.rocketlab.screen.auth.signup.presentation.model.SignUpScreenState
import io.rocketlab.screen.auth.signup.presentation.model.asContentOrNull
import io.rocketlab.service.auth.AuthService
import io.rocketlab.service.auth.exception.AuthServerTimeoutException
import kotlinx.coroutines.flow.update

class SignUpViewModel(
    private val authService: AuthService
) : BaseViewModel() {

    val uiState = state<SignUpScreenState>(SignUpScreenState.Content())
    val errorState = state<SignUpErrorState>(SignUpErrorState.None)

    val onErrorShowedAction = action<Unit> { onErrorShowed() }

    val updateEmailAction = action<String> { updateEmail(it) }
    val validateEmailAction = action<Unit> { validateEmail() }

    val updatePasswordAction = action<String> { updatePassword(it) }
    val validatePasswordAction = action<Unit> { validatePassword() }
    val updatePasswordVisibilityAction = action<Unit> { onPasswordVisibilityChanged() }

    val updatePasswordConfirmAction = action<String> { updatePasswordConfirm(it) }
    val validatePasswordConfirmAction = action<Unit> { validatePasswordConfirm() }
    val updatePasswordConfirmVisibilityAction = action<Unit> { onPasswordConfirmVisibilityChanged() }

    val registerClickedAction = action(::registerUser)

    private fun onErrorShowed() {
        errorState.update { SignUpErrorState.None }
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
                        error = content.getPasswordErrorOrEmpty()
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

    private fun updatePasswordConfirm(newValue: String) {
        uiState.value.asContentOrNull()?.let { content ->
            uiState.update {
                content.copy(
                    passwordConfirm = content.passwordConfirm.copy(
                        value = newValue,
                        error = ""
                    )
                )
            }
            if (content.password.value.length == newValue.length) {
                validatePasswordConfirm()
            }
        }
    }

    private fun validatePasswordConfirm() {
        uiState.value.asContentOrNull()?.let { content ->
            uiState.update {
                content.copy(
                    passwordConfirm = content.passwordConfirm.copy(
                        error = content.getPasswordConfirmErrorOrEmpty()
                    )
                )
            }
        }
    }

    private fun onPasswordConfirmVisibilityChanged() {
        uiState.value.asContentOrNull()?.let { content ->
            val isPasswordVisible = content.passwordConfirm.isVisible
            uiState.update {
                content.copy(
                    passwordConfirm = content.passwordConfirm.copy(
                        isVisible = isPasswordVisible.not()
                    )
                )
            }
        }
    }

    private fun registerUser(onRegistered: (() -> Unit)) {
        validateEmail()
        validatePassword()
        validatePasswordConfirm()

        val contentState = uiState.value.asContentOrNull() ?: return
        if (contentState.isFieldsValid) {
            uiState.update { SignUpScreenState.Loading }
            authService.registerUser(
                credentials = contentState.credentials,
                onSuccess = { onRegistered.invoke() },
                onFailure = { exception ->
                    val error = when (exception) {
                        is AuthServerTimeoutException -> SignUpErrorState.TimedOut
                        else -> SignUpErrorState.Unknown
                    }
                    uiState.update { contentState }
                    errorState.update { error }
                }
            )
        }
    }
}