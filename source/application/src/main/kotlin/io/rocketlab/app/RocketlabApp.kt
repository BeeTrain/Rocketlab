package io.rocketlab.app

import android.app.Application
import io.rocketlab.annotation.processing.annotation.KoinInitializer

@KoinInitializer
class RocketlabApp : Application()