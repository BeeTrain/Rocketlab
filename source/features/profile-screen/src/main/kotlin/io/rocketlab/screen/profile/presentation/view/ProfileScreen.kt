package io.rocketlab.screen.profile.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.profile.R
import io.rocketlab.screen.profile.presentation.model.ProfileScreenState
import io.rocketlab.screen.profile.presentation.viewmodel.ProfileViewModel
import io.rocketlab.ui.appbar.AppBar
import io.rocketlab.ui.progress.CircularProgress
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.profile_screen_title),
                onBackPressed = { viewModel.onBackPressedAction.accept() },
                actions = {
                    ProfileActions(
                        uiState = uiState,
                        viewModel = viewModel
                    )
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (uiState) {
                    is ProfileScreenState.Content -> ContentState(
                        uiState = uiState.asContent(),
                        viewModel = viewModel
                    )
                    is ProfileScreenState.Loading -> LoadingState()
                }
            }
        }
    )
}

@Composable
fun ProfileActions(
    uiState: ProfileScreenState,
    viewModel: ProfileViewModel
) {
    if (uiState is ProfileScreenState.Content && uiState.isLogged) {
        OutlinedButton(
            modifier = Modifier.height(28.dp),
            onClick = { viewModel.onLogOutPressedAction.accept() },
            content = {
                Text(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.outline,
                    text = stringResource(id = R.string.profile_log_out_title)
                )
            }
        )
    } else {
        Spacer(modifier = Modifier.size(0.dp))
    }
}

@Composable
private fun BoxScope.LoadingState() {
    CircularProgress(
        modifier = Modifier
            .size(56.dp)
            .align(Alignment.Center)
    )
}

@Composable
private fun BoxScope.ContentState(
    uiState: ProfileScreenState.Content,
    viewModel: ProfileViewModel
) {
    Column(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProfileImage(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = { viewModel.onProfileImagePressedAction.accept() }
            ),
            photoUrl = uiState.photoUrl
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { viewModel.onUserNamePressedAction.accept() },
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            text = uiState.userName
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            text = uiState.eMail
        )
    }
}

@Composable
fun ColumnScope.ProfileImage(
    modifier: Modifier = Modifier,
    photoUrl: String
) {
    Box(
        modifier = modifier
            .align(Alignment.CenterHorizontally)
            .size(92.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onBackground)
    ) {
        val photoImageModifier = Modifier
            .clip(CircleShape)
            .align(Alignment.Center)
            .size(88.dp)

        if (photoUrl.isNotEmpty()) {
            AsyncImage(
                modifier = photoImageModifier,
                model = photoUrl,
                contentDescription = null
            )
        } else {
            Icon(
                modifier = photoImageModifier,
                imageVector = Icons.Filled.AccountCircle,
                tint = MaterialTheme.colorScheme.background,
                contentDescription = null
            )
        }
    }
}