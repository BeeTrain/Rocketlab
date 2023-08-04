package io.rocketlab.navigation.api

sealed class Destination {

    val route: String
        get() = this::class.java.simpleName

    data object Splash : Destination()

    data object SignIn : Destination()

    data object SignUp : Destination()

    data object Home : Destination()

    data object Profile : Destination()

    data object NotesList : Destination()

    data object NoteEditor : Destination() {

        const val KEY_NOTE_ID = "NOTE_ID"
    }

    data object HeroSquad : Destination()
}