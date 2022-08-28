package io.rocketlab.ui.navigation

sealed class Destination(val route: String) {

    object Home : Destination("home")

    object Notes : Destination("notes")
}
