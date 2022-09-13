package io.rocketlab.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rocketlab.arch.lifecycle.SingleSharedFlow
import io.rocketlab.service.auth.AuthService
import io.rocketlab.splash.presentation.model.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DEFAULT_DELAY = 1000L

class SplashViewModel(
    private val authService: AuthService
) : ViewModel() {

    val uiState = SingleSharedFlow<SplashState>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(DEFAULT_DELAY)
            uiState.emit(
                SplashState.Data(
                    isLogged = authService.isLogged
                )
            )
        }
    }

    fun navigateToNextScreen(
        isLogged: Boolean,
        onLogged: () -> Unit,
        onNotLogged: () -> Unit
    ) {
        if (isLogged) {
            onLogged.invoke()
        } else {
            onNotLogged.invoke()
        }
    }
}