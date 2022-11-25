package io.rocketlab.screen.auth.presentation.signup.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Destination
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.screen.auth.presentation.mapper.SigningErrorMapper
import io.rocketlab.screen.auth.presentation.signup.presentation.model.SignUpErrorState
import io.rocketlab.screen.auth.presentation.signup.presentation.model.SignUpScreenState
import io.rocketlab.screen.auth.presentation.validation.SigningValidator
import io.rocketlab.service.auth.AuthService
import kotlinx.coroutines.flow.update

class SignUpViewModel(
    private val authService: AuthService,
    private val signingValidator: SigningValidator,
    private val errorMapper: SigningErrorMapper,
    private val navigator: Navigator
) : BaseViewModel() {

    val uiState = state<SignUpScreenState>(SignUpScreenState.Content())
    val errorState = state(SignUpErrorState())

    val onErrorShowedAction = action<Unit> { onErrorShowed() }
    val onBackPressedAction = action<Unit> { onBackPressed() }

    val updateEmailAction = action<String> { updateEmail(it) }
    val validateEmailAction = action<Unit> { validateEmail() }

    val updatePasswordAction = action<String> { updatePassword(it) }
    val validatePasswordAction = action<Unit> { validatePassword() }
    val updatePasswordVisibilityAction = action<Unit> { onPasswordVisibilityChanged() }

    val updatePasswordConfirmAction = action<String> { updatePasswordConfirm(it) }
    val validatePasswordConfirmAction = action<Unit> { validatePasswordConfirm() }
    val updatePasswordConfirmVisibilityAction =
        action<Unit> { onPasswordConfirmVisibilityChanged() }

    val registerClickedAction = action<Unit> { registerUser() }

    private fun onBackPressed() {
        navigator.navigateUp()
    }

    private fun onErrorShowed() {
        errorState.update { SignUpErrorState() }
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
                        error = signingValidator.getEmailErrorOrEmpty(content.eMail)
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
                        error = signingValidator.getPasswordErrorOrEmpty(content.password)
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
                        error = signingValidator.getPasswordConfirmErrorOrEmpty(
                            passwordField = content.password,
                            confirmField = content.passwordConfirm
                        )
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

    private fun registerUser() {
        validateEmail()
        validatePassword()
        validatePasswordConfirm()

        val contentState = uiState.value.asContentOrNull() ?: return
        if (contentState.isFieldsValid) {
            uiState.update { SignUpScreenState.Loading }
            authService.registerUser(
                credentials = contentState.credentials,
                onSuccess = { openHomeScreen() },
                onFailure = { exception -> handleError(exception, contentState) }
            )
        }
    }

    private fun handleError(exception: Throwable, contentState: SignUpScreenState.Content) {
        val error = errorMapper.map(exception)
        uiState.update { contentState }
        errorState.update { SignUpErrorState(error) }
    }

    private fun openHomeScreen() {
        navigator.navigate(Destination.Home) {
            popUpTo(Destination.Splash.route) {
                inclusive = true
            }
        }
    }
}