package io.rocketlab.navigation

sealed class Destination {

    val route: String
        get() = this::class.java.simpleName

    object Splash : Destination()

    object SignIn : Destination()

    object SignUp : Destination()

    object Home : Destination()

    object Notes : Destination()
}
