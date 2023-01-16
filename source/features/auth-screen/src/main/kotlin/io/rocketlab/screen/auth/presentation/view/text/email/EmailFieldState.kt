package io.rocketlab.screen.auth.presentation.view.text.email

import androidx.compose.runtime.Immutable

@Immutable
data class EmailFieldState(
    val value: String = "",
    val error: String = ""
) {

    val isValid = value.isNotEmpty() && error.isEmpty()
}