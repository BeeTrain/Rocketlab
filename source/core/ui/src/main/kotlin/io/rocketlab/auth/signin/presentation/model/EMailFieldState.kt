package io.rocketlab.auth.signin.presentation.model

data class EMailFieldState(
    val label: String = "E-Mail",
    val value: String = "",
    val error: String = ""
) {

    val isValid = value.isNotEmpty() && error.isEmpty()
}