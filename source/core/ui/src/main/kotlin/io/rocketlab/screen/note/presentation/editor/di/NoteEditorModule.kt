package io.rocketlab.screen.note.presentation.editor.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.note.presentation.editor.data.repository.NoteEditorRepository
import io.rocketlab.screen.note.presentation.editor.domain.interactor.NoteEditorInteractor
import io.rocketlab.screen.note.presentation.editor.presentation.viewmodel.NoteEditorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val noteEditorModule = module {

    viewModel { (noteId: Int?) -> NoteEditorViewModel(noteId, get(), get()) }

    factory { NoteEditorInteractor(get()) }

    factory { NoteEditorRepository(get()) }
}