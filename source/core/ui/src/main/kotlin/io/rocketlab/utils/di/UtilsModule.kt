package io.rocketlab.utils.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.utils.provider.activity.ActivityContextProvider
import io.rocketlab.utils.provider.activity.ActivityLifecycler
import io.rocketlab.utils.provider.resources.ResourcesProvider
import io.rocketlab.utils.system.AppManager
import io.rocketlab.utils.system.DeviceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@KoinModule
val utilsModule = module {

    single(createdAtStart = true) { ActivityContextProvider(androidApplication()) }
    single(createdAtStart = true) { ActivityLifecycler(androidApplication()) }

    single { ResourcesProvider(androidContext(), get()) }

    single { AppManager(androidContext(), get()) }
    single { DeviceManager(get()) }
}