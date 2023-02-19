package io.rocketlab.screen.home.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class HomeScreenState(
    val userName: String,
    val userPhotoUrl: String?,
    val listItems: List<HomeListItem>
)