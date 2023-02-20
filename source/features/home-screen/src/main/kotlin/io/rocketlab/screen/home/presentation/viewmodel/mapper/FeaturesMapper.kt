package io.rocketlab.screen.home.presentation.viewmodel.mapper

import io.rocketlab.screen.home.R
import io.rocketlab.screen.home.domain.model.Feature
import io.rocketlab.screen.home.presentation.model.HomeListItem
import io.rocketlab.utils.provider.resources.ResourcesProvider

class FeaturesMapper(
    private val resourcesProvider: ResourcesProvider
) {

    fun map(features: List<Feature>): List<HomeListItem> {
        return features.map { feature ->
            HomeListItem(
                title = mapTitle(feature),
                feature = feature
            )
        }
    }

    private fun mapTitle(feature: Feature): String {
        val titleRes = when (feature) {
            Feature.NOTES -> R.string.home_screen_notes_title
            Feature.HERO_SQUAD -> R.string.home_screen_hero_squad_title
        }

        return resourcesProvider.getString(titleRes)
    }
}