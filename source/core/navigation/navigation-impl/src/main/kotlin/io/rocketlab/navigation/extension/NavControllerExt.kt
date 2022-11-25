package io.rocketlab.navigation.extension

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import io.rocketlab.navigation.api.Destination

fun NavController.navigate(destination: Destination, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(destination.route, builder)
}