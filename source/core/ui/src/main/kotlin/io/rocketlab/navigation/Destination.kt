package io.rocketlab.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destination {

    val route: String
        get() = this::class.java.simpleName

    open val arguments: List<NamedNavArgument> = emptyList()

    private fun prepareArgs(): String {
        return if (arguments.isEmpty()) {
            ""
        } else {
            arguments
                .map { "/{${it.name}}" }
                .joinToString { it }
        }
    }

    object Splash : Destination()

    object SignIn : Destination()

    object SignUp : Destination()

    object Home : Destination()

    object NotesList : Destination()

    object NoteEditor : Destination() {

        const val KEY_NOTE_ID = "NOTE_ID"

        override val arguments: List<NamedNavArgument>
            get() = listOf(
                navArgument(KEY_NOTE_ID) { type = NavType.IntType }
            )
    }
}