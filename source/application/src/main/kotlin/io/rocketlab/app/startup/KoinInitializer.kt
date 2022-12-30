package io.rocketlab.app.startup

import android.content.Context
import androidx.startup.Initializer
import koin.annotation.processing.module.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

@Suppress("Unused")
class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            modules(koinModules)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}