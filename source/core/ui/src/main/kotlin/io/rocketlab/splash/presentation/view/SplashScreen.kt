package io.rocketlab.splash.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import io.rocketlab.splash.presentation.model.SplashState
import io.rocketlab.splash.presentation.viewmodel.SplashViewModel
import io.rocketlab.ui.R
import io.rocketlab.ui.theme.Brand1
import org.koin.androidx.compose.inject

@Composable
fun SplashScreen(
    onLogged: () -> Unit,
    onNotLogged: () -> Unit
) {
    val viewModel by inject<SplashViewModel>()
    val uiState by viewModel.uiState.collectAsState(SplashState.Loading)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brand1)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_rocket),
            contentDescription = "",
            modifier = Modifier.align(Alignment.Center)
        )
    }
    LaunchedEffect(
        key1 = uiState,
        block = {
            when (uiState) {
                SplashState.Loading -> Unit
                is SplashState.Data -> {
                    viewModel.navigateToNextScreen(
                        (uiState as SplashState.Data).isLogged,
                        onLogged,
                        onNotLogged
                    )
                }
            }
        }
    )
}
