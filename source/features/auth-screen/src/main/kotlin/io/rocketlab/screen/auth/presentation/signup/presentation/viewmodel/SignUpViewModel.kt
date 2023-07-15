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
    val updatePasswordConfirmVisibilityAction = action<Unit> { onPasswordConfirmVisibilityChanged() }

    val registerClickedAction = action<Unit> { registerUser() }

    private fun onBackPressed() {
        navigator.navigateUp()
    }

    private fun onErrorShowed() {
        errorState.update { SignUpErrorState() }
    }

    private fun updateEmail(newValue: String) {
        val content = uiState.value.asContentOrNull() ?: return

        uiState.update {
            content.copy(
                eMail = content.eMail.copy(
                    value = newValue,
                    error = ""
                )
            )
        }
    }

    private fun validateEmail() {
        val content = uiState.value.asContentOrNull() ?: return

        uiState.update {
            content.copy(
                eMail = content.eMail.copy(
                    error = signingValidator.getEmailErrorOrEmpty(content.eMail)
                )
            )
        }
    }

    private fun updatePassword(newValue: String) {
        val content = uiState.value.asContentOrNull() ?: return

        uiState.update {
            content.copy(
                password = content.password.copy(
                    value = newValue,
                    error = ""
                )
            )
        }
    }

    private fun validatePassword() {
        val content = uiState.value.asContentOrNull() ?: return

        uiState.update {
            content.copy(
                password = content.password.copy(
                    error = signingValidator.getPasswordErrorOrEmpty(content.password)
                )
            )
        }
    }

    private fun onPasswordVisibilityChanged() {
        val content = uiState.value.asContentOrNull() ?: return

        uiState.update {
            content.copy(
                password = content.password.copy(
                    isVisible = content.password.isVisible.not()
                )
            )
        }
    }

    private fun updatePasswordConfirm(newValue: String) {
        val content = uiState.value.asContentOrNull() ?: return

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

    private fun validatePasswordConfirm() {
        val content = uiState.value.asContentOrNull() ?: return

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

    private fun onPasswordConfirmVisibilityChanged() {
        val content = uiState.value.asContentOrNull() ?: return

        uiState.update {
            content.copy(
                passwordConfirm = content.passwordConfirm.copy(
                    isVisible = content.passwordConfirm.isVisible.not()
                )
            )
        }
    }

    private fun registerUser() {
        val content = uiState.value.asContentOrNull() ?: return

        validateEmail()
        validatePassword()
        validatePasswordConfirm()

        if (content.isFieldsValid) {
            uiState.update { SignUpScreenState.Loading }
            authService.registerUser(
                credentials = content.credentials,
                onSuccess = { openHomeScreen() },
                onFailure = { exception -> handleError(exception, content) }
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