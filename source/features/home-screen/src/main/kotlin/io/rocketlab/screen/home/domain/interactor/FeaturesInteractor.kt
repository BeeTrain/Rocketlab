package io.rocketlab.screen.home.domain.interactor

import io.rocketlab.screen.home.data.repository.FeaturesRepository
import io.rocketlab.screen.home.domain.model.Feature

class FeaturesInteractor(
    private val featuresRepository: FeaturesRepository
) {

    fun loadFeatures(): List<Feature> {
        return featuresRepository.getFeatures()
    }
}