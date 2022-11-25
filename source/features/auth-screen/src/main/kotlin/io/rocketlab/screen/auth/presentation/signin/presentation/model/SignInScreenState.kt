package io.rocketlab.screen.auth.presentation.signin.presentation.model

import androidx.compose.runtime.Immutable
import io.rocketlab.screen.auth.presentation.view.text.email.EmailFieldState
import io.rocketlab.screen.auth.presentation.view.text.password.PasswordFieldState
import io.rocketlab.service.auth.model.Credentials

@Immutable
sealed interface SignInScreenState {

    @Immutable
    object Loading : SignInScreenState

    @Immutable
    data class Content(
        val eMail: EmailFieldState = EmailFieldState(),
        val password: PasswordFieldState = PasswordFieldState()
    ) : SignInScreenState {

        val isFieldsValid = eMail.isValid && password.isValid

        val credentials: Credentials
            get() = Credentials(eMail.value, password.value)
    }

    fun asContent() = this as Content

    fun asContentOrNull() = this as? Content
}

@Immutable
data class SignInErrorState(val message: String = "")