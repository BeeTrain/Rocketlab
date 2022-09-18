package io.rocketlab.screen.auth.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

data class PasswordFieldState(
    val label: String = "Password",
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

    val description = if (isVisible) {
        "Show password"
    } else {
        "Hide password"
    }
}