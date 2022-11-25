package io.rocketlab.navigation.api

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

interface Navigator {

    fun bind(navController: NavController)

    fun <T> parameter(key: String): T?

    fun navigateUp()

    fun navigate(
        destination: Destination,
        arguments: Map<String, Any> = mapOf(),
        builder: NavOptionsBuilder.() -> Unit = {}
    )
}