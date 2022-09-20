package io.rocketlab.screen.auth.presentation.signup.presentation.model

import io.rocketlab.screen.auth.presentation.view.text.email.EmailFieldState
import io.rocketlab.screen.auth.presentation.view.text.password.PasswordFieldState
import io.rocketlab.service.auth.model.Credentials

sealed interface SignUpScreenState {

    object Loading : SignUpScreenState

    data class Content(
        val eMail: EmailFieldState = EmailFieldState(),
        val password: PasswordFieldState = PasswordFieldState(),
        val passwordConfirm: PasswordFieldState = PasswordFieldState()
    ) : SignUpScreenState {

        val isFieldsValid = eMail.isValid &&
            password.isValid &&
            passwordConfirm.isValid &&
            password.value == passwordConfirm.value

        val credentials: Credentials
            get() = Credentials(eMail.value, password.value)
    }

    fun asContent() = this as Content

    fun asContentOrNull() = this as? Content
}

data class SignUpErrorState(val message: String = "")