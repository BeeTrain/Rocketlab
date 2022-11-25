package io.rocketlab.screen.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rocketlab.arch.extension.action
import io.rocketlab.navigation.api.Destination
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.service.auth.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DEFAULT_DELAY = 1000L

class SplashViewModel(
    private val authService: AuthService,
    private val navigator: Navigator
) : ViewModel() {

    val loadDataAction = action<Unit> { loadData() }

    private fun loadData() {
        viewModelScope.launch {
            delay(DEFAULT_DELAY)
            navigateToNextScreen(authService.isLogged)
        }
    }

    private fun navigateToNextScreen(isLogged: Boolean) {
        if (isLogged) {
            openAuthScreen()
        } else {
            openHomeScreen()
        }
    }

    private fun openAuthScreen() {
        navigator.navigate(Destination.Home) {
            popUpTo(Destination.Splash.route) {
                inclusive = true
            }
        }
    }

    private fun openHomeScreen() {
        navigator.navigate(Destination.SignIn) {
            popUpTo(Destination.Splash.route) {
                inclusive = true
            }
        }
    }
}