package io.rocketlab.screen.auth.presentation.view.text.password

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import io.rocketlab.ui.R

data class PasswordFieldState(
    val value: String = "",
    val error: String = "",
    val isVisible: Boolean = false
) {

    val isValid = value.isNotEmpty() && error.isEmpty()

    val passwordTransformation = if (isVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    val visibilityIcon = if (isVisible) {
        Icons.Filled.VisibilityOff
    } else {
        Icons.Filled.Visibility
    }

    val descriptionRes = if (isVisible) {
        R.string.password_field_hide_password
    } else {
        R.string.password_field_show_password
    }
}