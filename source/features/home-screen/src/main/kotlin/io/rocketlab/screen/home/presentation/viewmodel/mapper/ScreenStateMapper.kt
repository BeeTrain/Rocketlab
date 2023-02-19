package io.rocketlab.screen.home.presentation.viewmodel.mapper

import io.rocketlab.screen.home.R
import io.rocketlab.screen.home.domain.model.Feature
import io.rocketlab.screen.home.presentation.model.HomeScreenState
import io.rocketlab.service.auth.model.User
import io.rocketlab.utils.extension.ifNull
import io.rocketlab.utils.provider.resources.ResourcesProvider

class ScreenStateMapper(
    private val resourcesProvider: ResourcesProvider,
    private val featuresMapper: FeaturesMapper
) {

    fun map(user: User?, features: List<Feature>): HomeScreenState {
        return HomeScreenState(
            userName = user?.name.ifNull(resourcesProvider.getString(R.string.home_screen_profile_title)),
            userPhotoUrl = user?.photoUrl,
            listItems = featuresMapper.map(features)
        )
    }
}