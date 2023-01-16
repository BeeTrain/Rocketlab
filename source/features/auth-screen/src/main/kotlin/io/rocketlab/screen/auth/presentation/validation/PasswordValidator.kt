package io.rocketlab.screen.auth.presentation.validation

import io.rocketlab.screen.auth.R
import io.rocketlab.screen.auth.presentation.view.text.password.PasswordFieldState
import io.rocketlab.utils.provider.resources.ResourcesProvider

class PasswordValidator(
    private val resourcesProvider: ResourcesProvider
) {

    fun getPasswordErrorOrEmpty(password: PasswordFieldState): String {
        return when {
            password.value.isEmpty() -> resourcesProvider.getString(R.string.password_field_empty_error_text)
            password.value.length < 8 -> resourcesProvider.getString(R.string.password_field_invalid_error_text)
            else -> ""
        }
    }

    fun getConfirmErrorOrEmpty(password: PasswordFieldState, confirm: PasswordFieldState): String {
        return when {
            password.value != confirm.value -> resourcesProvider.getString(R.string.password_confirm_error_text)
            else -> ""
        }
    }
}