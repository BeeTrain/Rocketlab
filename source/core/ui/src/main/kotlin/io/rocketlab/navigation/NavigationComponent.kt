package io.rocketlab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import io.rocketlab.navigation.extension.NavHost
import io.rocketlab.navigation.extension.composable
import io.rocketlab.navigation.extension.navigate
import io.rocketlab.screen.auth.presentation.signin.presentation.view.SignInScreen
import io.rocketlab.screen.auth.presentation.signup.presentation.view.SignUpScreen
import io.rocketlab.screen.home.presentation.HomeScreen
import io.rocketlab.screen.notes.presentation.NotesScreen
import io.rocketlab.screen.splash.presentation.view.SplashScreen

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
                    onRegisterClicked = { navController.navigate(Destination.SignUp) },
                    onLogged = {
                        navController.navigate(Destination.Home) {
                            popUpTo(Destination.SignIn.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Destination.SignUp) {
                SignUpScreen {
                    navController.navigate(Destination.Home) {
                        popUpTo(Destination.Splash.route) {
                            inclusive = true
                        }
                    }
                }
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