package io.rocketlab.screen.home.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.home.R
import io.rocketlab.screen.home.presentation.viewmodel.HomeScreenViewModel
import io.rocketlab.ui.appbar.AppBar
import io.rocketlab.ui.theme.fabShape
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = getViewModel(),
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(stringResource(R.string.home_screen_empty_state_image_url))
    )
    Scaffold(
        topBar = { AppBar(title = stringResource(id = R.string.home_screen_title)) },
        modifier = Modifier
            .navigationBarsPadding(),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LottieAnimation(composition)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = fabShape,
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { viewModel.onNotesClickedAction.accept() },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Notes,
                        contentDescription = stringResource(R.string.home_screen_notes_title),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    )
}