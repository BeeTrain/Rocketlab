package io.rocketlab.utils.system.config

import io.rocketlab.utils.system.config.model.BuildType

interface Environment {

    val isDebug: Boolean

    val isMainProcess: Boolean

    val buildType: BuildType
}