package io.rocketlab.auth.signup.presentation.model

import io.rocketlab.auth.model.EMailFieldState
import io.rocketlab.auth.model.PasswordFieldState
import io.rocketlab.service.auth.model.Credentials
import io.rocketlab.utils.extension.isEmailValid

sealed interface SignUpScreenState {

    object Loading : SignUpScreenState

    data class Content(
        val eMail: EMailFieldState = EMailFieldState(),
        val password: PasswordFieldState = PasswordFieldState(),
        val passwordConfirm: PasswordFieldState = PasswordFieldState("Confirm password")
    ) : SignUpScreenState {

        val isFieldsValid = eMail.isValid &&
            password.isValid &&
            passwordConfirm.isValid &&
            password.value == passwordConfirm.value

        val credentials: Credentials
            get() = Credentials(eMail.value, password.value)

        fun getEmailErrorOrEmpty(): String {
            return if (eMail.value.isEmailValid().not()) {
                "Enter correct e-mail"
            } else {
                ""
            }
        }

        fun getPasswordErrorOrEmpty(): String {
            return if (password.value.length < 8) {
                "Password too short"
            } else {
                ""
            }
        }

        fun getPasswordConfirmErrorOrEmpty(): String {
            return if (password.value != passwordConfirm.value) {
                "Passwords should match"
            } else {
                ""
            }
        }
    }
}

sealed class SignUpErrorState(val message: String) {

    object None : SignUpErrorState("")

    object TimedOut : SignUpErrorState("Server not responding")

    object Unknown : SignUpErrorState("Something went wrong")

}

fun SignUpScreenState.asContent() = this as SignUpScreenState.Content

fun SignUpScreenState.asContentOrNull() = this as? SignUpScreenState.Content