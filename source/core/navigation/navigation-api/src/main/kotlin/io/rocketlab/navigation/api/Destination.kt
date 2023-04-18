package io.rocketlab.navigation.api

sealed class Destination {

    val route: String
        get() = this::class.java.simpleName

    object Splash : Destination()

    object SignIn : Destination()

    object SignUp : Destination()

    object Home : Destination()

    object Profile : Destination()

    object NotesList : Destination()

    object NoteEditor : Destination() {

        const val KEY_NOTE_ID = "NOTE_ID"
    }

    object HeroSquad : Destination()
}