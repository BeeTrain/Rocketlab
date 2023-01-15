package io.rocketlab.screen.note.editor.domain.interactor

import io.rocketlab.screen.note.editor.data.repository.NoteEditorRepository
import io.rocketlab.storage.database.model.Note

class NoteEditorInteractor(
    private val noteEditorRepository: NoteEditorRepository
) {

    suspend fun saveNote(note: Note) {
        noteEditorRepository.addNote(note)
    }

    suspend fun loadNote(id: Int?): Note {
        return noteEditorRepository.getNote(id)
    }

    suspend fun deleteNote(id: Int) {
        noteEditorRepository.deleteNote(id)
    }
}