package io.rocketlab.screen.notes.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.notes.presentation.viewmodel.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val notesModule = module {

    viewModel { NotesViewModel(get()) }
}