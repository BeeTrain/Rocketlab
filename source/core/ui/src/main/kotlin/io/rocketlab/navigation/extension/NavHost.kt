package io.rocketlab.navigation.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import io.rocketlab.navigation.Destination
import androidx.navigation.compose.NavHost as ComposeNavHost

@Composable
public fun NavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    ComposeNavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
        route = route,
        builder = builder
    )
}