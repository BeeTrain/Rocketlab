package io.rocketlab.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import io.rocketlab.arch.extension.addAll
import io.rocketlab.arch.extension.clear
import io.rocketlab.navigation.extension.isLifecycleResumed
import io.rocketlab.navigation.extension.navigate

class Navigator(
    private val savedStateHandle: SavedStateHandle
) {

    private var navigationController: NavController? = null

    fun bind(navController: NavController) {
        navigationController = navController
    }

    fun navigateUp() {
        requireNotNull(navigationController).navigateUp()
    }

    fun navigate(
        destination: Destination,
        arguments: Map<String, Any> = mapOf(),
        builder: NavOptionsBuilder.() -> Unit = {}
    ) {
        requireNotNull(navigationController).apply {
            if (currentBackStackEntry?.isLifecycleResumed == true) {
                updateArguments(arguments)
                navigate(destination, builder)
            }
        }
    }

    private fun updateArguments(arguments: Map<String, Any>) {
        savedStateHandle.apply {
            clear()
            addAll(arguments)
        }
    }
}