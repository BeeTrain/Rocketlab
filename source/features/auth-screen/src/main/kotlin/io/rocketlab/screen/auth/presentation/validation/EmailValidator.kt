package io.rocketlab.screen.auth.presentation.validation

import io.rocketlab.screen.auth.presentation.view.text.email.EmailFieldState
import io.rocketlab.ui.R
import io.rocketlab.utils.EMPTY
import io.rocketlab.utils.extension.isEmailValid
import io.rocketlab.utils.provider.resources.ResourcesProvider

class EmailValidator(
    private val resourcesProvider: ResourcesProvider
) {

    fun getEmailErrorOrEmpty(email: EmailFieldState): String {
        return when {
            email.value.isEmpty() -> resourcesProvider.getString(R.string.e_mail_field_empty_error_text)
            email.value.isEmailValid().not() -> resourcesProvider.getString(R.string.e_mail_field_empty_error_text)
            else -> EMPTY
        }
    }
}