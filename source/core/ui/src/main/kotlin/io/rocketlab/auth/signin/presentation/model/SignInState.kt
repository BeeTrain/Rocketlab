package io.rocketlab.auth.signin.presentation.model

import io.rocketlab.service.auth.model.Credentials
import io.rocketlab.utils.extension.isEmailValid

sealed interface SignInState {

    object Loading : SignInState

    data class Content(
        val eMail: EMailFieldState = EMailFieldState(),
        val password: PasswordFieldState = PasswordFieldState(),
        val error: Error? = null
    ) : SignInState {

        val isLoginButtonEnabled = eMail.isValid && password.isValid

        fun validate(
            onValid: (Credentials) -> Unit,
            onNotValid: (Content) -> Unit
        ) {
            val emailFieldError = getEmailErrorOrNull(eMail.value).orEmpty()
            val passwordFieldError = getPasswordErrorOrNull(password.value).orEmpty()

            when {
                emailFieldError.isNotEmpty() -> {
                    onNotValid.invoke(copy(eMail = eMail.copy(error = emailFieldError)))
                }
                passwordFieldError.isNotEmpty() -> {
                    onNotValid.invoke(copy(password = password.copy(error = passwordFieldError)))
                }
                else -> onValid.invoke(Credentials(eMail.value, password.value))
            }
        }

        private fun getEmailErrorOrNull(email: String): String? {
            return if (email.isEmailValid().not()) {
                "Enter correct e-mail"
            } else {
                null
            }
        }

        private fun getPasswordErrorOrNull(password: String): String? {
            return if (password.length < 8) {
                "Password too short"
            } else {
                null
            }
        }
    }

    sealed class Error(val message: String) : SignInState {

        object UserNotFound : Error("User not found")

        object TimedOut : Error("Server not responding")

        object Unknown : Error("Something went wrong")
    }
}