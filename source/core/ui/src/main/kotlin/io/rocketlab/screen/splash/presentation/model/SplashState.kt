package io.rocketlab.screen.splash.presentation.model

sealed interface SplashState {

    object Loading : SplashState

    data class Data(
        val isLogged: Boolean
    ) : SplashState
}
