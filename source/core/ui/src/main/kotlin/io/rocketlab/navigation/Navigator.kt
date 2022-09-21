package io.rocketlab.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import io.rocketlab.navigation.extension.navigate

class Navigator {

    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun navigateUp() {
        requireNotNull(navController).navigateUp()
    }

    fun navigate(destination: Destination, builder: NavOptionsBuilder.() -> Unit = {}) {
        requireNotNull(navController).navigate(destination, builder)
    }
}