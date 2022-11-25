package io.rocketlab.screen.note.editor.domain.interactor

import io.rocketlab.screen.note.editor.data.repository.NoteEditorRepository

class NoteEditorInteractor(
    private val noteEditorRepository: NoteEditorRepository
) {

    suspend fun saveNote(note: io.rocketlab.storage.database.model.Note) {
        noteEditorRepository.addNote(note)
    }

    suspend fun loadNote(id: Int?): io.rocketlab.storage.database.model.Note {
        return noteEditorRepository.getNote(id)
    }

    suspend fun deleteNote(id: Int) {
        noteEditorRepository.deleteNote(id)
    }
}