package io.rocketlab.screen.auth.presentation.view.text.email

data class EmailFieldState(
    val value: String = "",
    val error: String = ""
) {

    val isValid = value.isNotEmpty() && error.isEmpty()
}