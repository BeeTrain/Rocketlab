package io.rocketlab.screen.splash.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.splash.presentation.viewmodel.SplashViewModel
import io.rocketlab.ui.R
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = getViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_rocket),
            contentDescription = "",
            modifier = Modifier.align(Alignment.Center)
        )
    }
    LaunchedEffect(viewModel) {
        viewModel.loadDataAction.accept()
    }
}
