package io.rocketlab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import io.rocketlab.navigation.extension.NavHost
import io.rocketlab.navigation.extension.composable
import io.rocketlab.screen.auth.presentation.signin.presentation.view.SignInScreen
import io.rocketlab.screen.auth.presentation.signup.presentation.view.SignUpScreen
import io.rocketlab.screen.home.presentation.view.HomeScreen
import io.rocketlab.screen.note.presentation.editor.presentation.view.NoteEditorScreen
import io.rocketlab.screen.note.presentation.list.presentation.view.NotesListScreen
import io.rocketlab.screen.splash.presentation.view.SplashScreen
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun NavigationComponent(
    navigator: Navigator = get()
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    navigator.bind(navController)

    ModalBottomSheetLayout(bottomSheetNavigator) {
        NavHost(
            navController = navController,
            startDestination = Destination.Splash
        ) {
            composable(Destination.Splash) {
                SplashScreen()
            }
            composable(Destination.SignIn) {
                SignInScreen()
            }
            composable(Destination.SignUp) {
                SignUpScreen()
            }
            composable(Destination.Home) {
                HomeScreen()
            }
            composable(Destination.NotesList) {
                NotesListScreen()
            }
            composable(Destination.NoteEditor) {
                NoteEditorScreen()
            }
        }
    }
}