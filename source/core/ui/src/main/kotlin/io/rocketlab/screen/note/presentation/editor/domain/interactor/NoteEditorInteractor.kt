package io.rocketlab.screen.note.presentation.editor.domain.interactor

import io.rocketlab.screen.note.data.model.Note
import io.rocketlab.screen.note.presentation.editor.data.repository.NoteEditorRepository

class NoteEditorInteractor(
    private val noteEditorRepository: NoteEditorRepository
) {

    suspend fun saveNote(note: Note) {
        noteEditorRepository.addNote(note)
    }
}