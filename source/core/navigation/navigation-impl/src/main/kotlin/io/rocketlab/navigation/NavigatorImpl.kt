package io.rocketlab.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import io.rocketlab.arch.extension.addAll
import io.rocketlab.arch.extension.clear
import io.rocketlab.navigation.api.Destination
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.navigation.extension.isLifecycleResumed
import io.rocketlab.navigation.extension.navigate

class NavigatorImpl(
    private val savedStateHandle: SavedStateHandle
) : Navigator {

    private var navigationController: NavController? = null

    override fun bind(navController: NavController) {
        navigationController = navController
    }

    override fun <T> parameter(key: String): T? {
        return savedStateHandle[key]
    }

    override fun navigateUp() {
        requireNotNull(navigationController).navigateUp()
    }

    override fun navigate(
        destination: Destination,
        arguments: Map<String, Any>,
        builder: NavOptionsBuilder.() -> Unit
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