package io.rocketlab.screen.home.data.repository

import io.rocketlab.screen.home.domain.model.Feature

class FeaturesRepository {

    fun getFeatures(): List<Feature> {
        return Feature.values().toList()
    }
}