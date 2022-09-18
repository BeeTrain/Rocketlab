package io.rocketlab.screen.auth.model

data class EMailFieldState(
    val label: String = "E-Mail",
    val value: String = "",
    val error: String = ""
) {

    val isValid = value.isNotEmpty() && error.isEmpty()
}