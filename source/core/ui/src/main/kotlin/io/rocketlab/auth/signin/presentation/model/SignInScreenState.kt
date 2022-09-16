package io.rocketlab.auth.signin.presentation.model

import io.rocketlab.auth.model.EMailFieldState
import io.rocketlab.auth.model.PasswordFieldState
import io.rocketlab.service.auth.model.Credentials
import io.rocketlab.utils.extension.isEmailValid

sealed interface SignInScreenState {

    object Loading : SignInScreenState

    data class Content(
        val eMail: EMailFieldState = EMailFieldState(),
        val password: PasswordFieldState = PasswordFieldState()
    ) : SignInScreenState {

        val isFieldsValid = eMail.isValid && password.isValid

        val credentials: Credentials
            get() = Credentials(eMail.value, password.value)

        fun getEmailErrorOrEmpty(): String {
            return if (eMail.value.isEmailValid().not()) {
                "Enter correct e-mail"
            } else {
                ""
            }
        }

        fun getPasswordErrorOrNull(): String {
            return if (password.value.length < 8) {
                "Password too short"
            } else {
                ""
            }
        }
    }
}

fun SignInScreenState.asContent() = this as SignInScreenState.Content

fun SignInScreenState.asContentOrNull() = this as? SignInScreenState.Content

sealed class SignInErrorState(val message: String) {

    object None : SignInErrorState("")

    object UserNotFound : SignInErrorState("User not found")

    object TimedOut : SignInErrorState("Server not responding")

    object Unknown : SignInErrorState("Something went wrong")
}