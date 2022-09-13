package io.rocketlab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import io.rocketlab.auth.signin.presentation.view.SignInScreen
import io.rocketlab.auth.signup.presentation.view.SignUpScreen
import io.rocketlab.home.presentation.HomeScreen
import io.rocketlab.navigation.extension.NavHost
import io.rocketlab.navigation.extension.composable
import io.rocketlab.navigation.extension.navigate
import io.rocketlab.notes.presentation.NotesScreen
import io.rocketlab.splash.presentation.view.SplashScreen

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun NavigationComponent() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    ModalBottomSheetLayout(bottomSheetNavigator) {
        NavHost(
            navController = navController,
            startDestination = Destination.Splash
        ) {
            composable(Destination.Splash) {
                SplashScreen(
                    onLogged = {
                        navController.navigate(Destination.Home) {
                            popUpTo(Destination.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                    onNotLogged = {
                        navController.navigate(Destination.SignIn) {
                            popUpTo(Destination.Splash.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Destination.SignIn) {
                SignInScreen(
                    { navController.navigate(Destination.SignUp) },
                    { navController.navigate(Destination.Home) }
                )
            }
            composable(Destination.SignUp) {
                SignUpScreen()
            }
            composable(Destination.Home) {
                HomeScreen {
                    navController.navigate(Destination.Notes)
                }
            }
            composable(Destination.Notes) {
                NotesScreen()
            }
        }
    }
}