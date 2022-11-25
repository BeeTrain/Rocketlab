package io.rocketlab.screen.note.editor.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.note.editor.data.repository.NoteEditorRepository
import io.rocketlab.screen.note.editor.domain.interactor.NoteEditorInteractor
import io.rocketlab.screen.note.editor.presentation.viewmodel.NoteEditorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val noteEditorModule = module {

    viewModel { NoteEditorViewModel(get(), get()) }

    factory { NoteEditorInteractor(get()) }

    factory { NoteEditorRepository(get()) }
}