package io.rocketlab.screen.home.presentation.model

import androidx.compose.runtime.Immutable
import io.rocketlab.screen.home.domain.model.Feature

@Immutable
data class HomeListItem(
    val title: String,
    val feature: Feature
)