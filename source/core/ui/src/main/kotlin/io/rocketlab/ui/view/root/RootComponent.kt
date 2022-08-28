package io.rocketlab.ui.view.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import io.rocketlab.home.presentation.HomeScreen
import io.rocketlab.home.presentation.NotesScreen
import io.rocketlab.ui.navigation.Destination
import io.rocketlab.ui.theme.RocketlabTheme

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun RootComponent() {
    RocketlabTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberNavController(bottomSheetNavigator)

            ModalBottomSheetLayout(bottomSheetNavigator) {
                NavHost(
                    navController = navController,
                    startDestination = Destination.Home.route
                ) {
                    composable(Destination.Home.route) {
                        HomeScreen { navController.navigate(it) }
                    }
                    composable(Destination.Notes.route) {
                        NotesScreen()
                    }
                }
            }
        }
    }
}