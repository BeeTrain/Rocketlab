package io.rocketlab.app

import android.app.Application
import io.rocketlab.annotation.processing.annotation.KoinInitializer
import koin.annotation.processing.module.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@KoinInitializer
class RocketlabApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@RocketlabApp)
            modules(koinModules)
        }
    }
}