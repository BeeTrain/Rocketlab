package io.rocketlab.storage.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.storage.database.AppDatabase
import io.rocketlab.storage.plain.SimpleDataStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@KoinModule
val storageModule = module {

    single { SimpleDataStorage.Provider.provide(androidContext()) }

    single { AppDatabase.Provider.provide(androidContext()) }

    single { get<AppDatabase>().noteDao() }
}