package io.rocketlab.screen.auth.presentation.signin.presentation.viewmodel

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.command
import io.rocketlab.arch.extension.emit
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.screen.auth.presentation.mapper.SigningErrorMapper
import io.rocketlab.screen.auth.presentation.signin.presentation.model.SignInErrorState
import io.rocketlab.screen.auth.presentation.signin.presentation.model.SignInScreenState
import io.rocketlab.screen.auth.presentation.validation.SigningValidator
import io.rocketlab.service.auth.AuthService
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val authService: AuthService,
    private val signingValidator: SigningValidator,
    private val errorMapper: SigningErrorMapper
) : BaseViewModel() {

    val uiState = state<SignInScreenState>(SignInScreenState.Content())
    val errorState = state(SignInErrorState())

    val openSignUpCommand = command<Unit>()
    val onLoggedCommand = command<Unit>()
    val launchGoogleSignCommand = command<Intent>()

    val onErrorShowedAction = action<Unit> { onErrorShowed() }

    val updateEmailAction = action<String> { updateEmail(it) }
    val validateEmailAction = action<Unit> { validateEmail() }

    val updatePasswordAction = action(::updatePassword)
    val validatePasswordAction = action<Unit> { validatePassword() }
    val updatePasswordVisibilityAction = action<Unit> { onPasswordVisibilityChanged() }

    val loginClickedAction = action<Unit> { onLoginClicked() }
    val registerClickedAction = action<Unit> { onRegisterClicked() }
    val googleSignClickedAction = action<Unit> { onGoogleSignClick() }
    val onGoogleAccountReceivedAction = action(::authWithGoogle)

    private fun onErrorShowed() {
        errorState.update { SignInErrorState() }
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

    private fun onLoginClicked() {
        validateEmail()
        validatePassword()

        val contentState = uiState.value.asContentOrNull() ?: SignInScreenState.Content()
        if (contentState.isFieldsValid) {
            uiState.update { SignInScreenState.Loading }
            authService.signInWithCredentials(
                contentState.credentials,
                onSuccess = { launch { onLoggedCommand.emit() } },
                onFailure = { exception -> handleError(exception, contentState) }
            )
        }
    }

    private fun onRegisterClicked() {
        launch {
            openSignUpCommand.emit()
        }
    }

    private fun onGoogleSignClick() {
        launch {
            val googleSignIntent = authService.getGoogleSignInIntent()
            launchGoogleSignCommand.emit(googleSignIntent)
        }
    }

    private fun authWithGoogle(account: GoogleSignInAccount) {
        val contentState = uiState.value.asContentOrNull() ?: SignInScreenState.Content()

        authService.signInWithGoogleSign(
            account = account,
            onSuccess = { launch { onLoggedCommand.emit() } },
            onFailure = { exception -> handleError(exception, contentState) }
        )
    }

    private fun handleError(exception: Throwable, contentState: SignInScreenState.Content) {
        val error = errorMapper.map(exception)
        uiState.update { contentState }
        errorState.update { SignInErrorState(error) }
    }
}