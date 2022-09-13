package io.rocketlab.navigation.extension

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import io.rocketlab.navigation.Destination
import androidx.navigation.compose.composable as composableX

fun NavGraphBuilder.composable(
    destination: Destination,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composableX(
        route = destination.route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content
    )
}