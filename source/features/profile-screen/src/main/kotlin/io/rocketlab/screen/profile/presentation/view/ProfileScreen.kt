package io.rocketlab.screen.profile.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.profile.R
import io.rocketlab.screen.profile.presentation.viewmodel.ProfileViewModel
import io.rocketlab.ui.appbar.AppBar
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = getViewModel()
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.profile_screen_title),
                onBackPressed = { viewModel.onBackPressedAction.accept() }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                content = { ContentState(viewModel = viewModel) }
            )
        }
    )
}

@Composable
private fun BoxScope.ContentState(
    viewModel: ProfileViewModel
) {
    Text(
        modifier = Modifier.align(Alignment.Center),
        style = MaterialTheme.typography.bodyLarge,
        text = stringResource(id = R.string.profile_screen_title)
    )
}