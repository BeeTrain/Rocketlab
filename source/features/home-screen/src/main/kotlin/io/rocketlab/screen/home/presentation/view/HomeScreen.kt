package io.rocketlab.screen.home.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.home.R
import io.rocketlab.screen.home.presentation.model.HomeListItem
import io.rocketlab.screen.home.presentation.view.appbar.HomeAppBar
import io.rocketlab.screen.home.presentation.view.list.HomeList
import io.rocketlab.screen.home.presentation.viewmodel.HomeScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = getViewModel(),
) {
    val screenState by viewModel.screenState.collectAsState()

    Scaffold(
        topBar = {
            HomeAppBar(
                userName = screenState.userName,
                userPhotoUrl = screenState.userPhotoUrl,
                onClick = { viewModel.onProfileHeaderClickAction.accept() }
            )
        },
        modifier = Modifier
            .navigationBarsPadding(),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                content = {
                    ContentState(
                        viewModel = viewModel,
                        homeListItems = screenState.listItems
                    )
                }
            )
        }
    )
}

@Composable
private fun ContentState(
    viewModel: HomeScreenViewModel,
    homeListItems: List<HomeListItem>
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(stringResource(R.string.home_screen_empty_state_image_url))
    )

    if (homeListItems.isEmpty()) {
        LottieAnimation(composition)
    } else {
        HomeList(
            listItems = homeListItems,
            onItemClick = { viewModel.onFeatureClickedAction.accept(it) }
        )
    }
}