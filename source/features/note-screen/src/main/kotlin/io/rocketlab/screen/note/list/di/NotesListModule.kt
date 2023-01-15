package io.rocketlab.screen.note.list.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.note.list.data.repository.NotesListRepository
import io.rocketlab.screen.note.list.domain.interactor.NotesListInteractor
import io.rocketlab.screen.note.list.presentation.viewmodel.NotesListViewModel
import io.rocketlab.screen.note.list.presentation.viewmodel.mapper.NoteColumnTitleMapper
import io.rocketlab.screen.note.list.presentation.viewmodel.mapper.NoteModelMapper
import io.rocketlab.screen.note.list.presentation.viewmodel.mapper.NotesColumnMapper
import io.rocketlab.screen.note.list.presentation.viewmodel.mapper.NotesListContentMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val notesListModule = module {

    viewModel { NotesListViewModel(get(), get(), get()) }

    factory { NotesListInteractor(get()) }

    factory { NotesListRepository(get()) }

    factory { NotesListContentMapper(get()) }

    factory { NoteModelMapper() }

    factory { NotesColumnMapper(get(), get()) }

    factory { NoteColumnTitleMapper(get()) }
}