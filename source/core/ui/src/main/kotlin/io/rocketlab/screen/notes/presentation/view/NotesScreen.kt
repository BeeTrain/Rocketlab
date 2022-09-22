package io.rocketlab.screen.notes.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.notes.presentation.viewmodel.NotesViewModel
import io.rocketlab.ui.R
import io.rocketlab.ui.appbar.AppBar
import org.koin.androidx.compose.getViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = getViewModel()
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(stringResource(R.string.notes_screen_empty_state_image_url))
    )
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.notes_screen_title),
                onBackPressed = { viewModel.onBackPressedAction.accept() }
            )
        },
        modifier = Modifier
            .navigationBarsPadding(),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    )
}