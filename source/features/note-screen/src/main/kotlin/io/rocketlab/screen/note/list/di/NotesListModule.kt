package io.rocketlab.screen.note.list.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.note.list.data.repository.NotesListRepository
import io.rocketlab.screen.note.list.domain.interactor.NotesListInteractor
import io.rocketlab.screen.note.list.presentation.viewmodel.NotesListViewModel
import io.rocketlab.screen.note.list.presentation.viewmodel.mapper.NotesListMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val notesListModule = module {

    viewModel { NotesListViewModel(get(), get(), get()) }

    factory { NotesListInteractor(get()) }

    factory { NotesListRepository(get()) }

    factory { NotesListMapper() }
}