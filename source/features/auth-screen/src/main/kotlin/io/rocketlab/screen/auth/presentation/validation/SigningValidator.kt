package io.rocketlab.screen.auth.presentation.validation

import io.rocketlab.screen.auth.presentation.view.text.email.EmailFieldState
import io.rocketlab.screen.auth.presentation.view.text.password.PasswordFieldState

class SigningValidator(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
) {

    fun getEmailErrorOrEmpty(emailFieldState: EmailFieldState): String {
        return emailValidator.getEmailErrorOrEmpty(emailFieldState)
    }

    fun getPasswordErrorOrEmpty(passwordFieldState: PasswordFieldState): String {
        return passwordValidator.getPasswordErrorOrEmpty(passwordFieldState)
    }

    fun getPasswordConfirmErrorOrEmpty(
        passwordField: PasswordFieldState,
        confirmField: PasswordFieldState
    ): String {
        return passwordValidator.getConfirmErrorOrEmpty(passwordField, confirmField)
    }
}