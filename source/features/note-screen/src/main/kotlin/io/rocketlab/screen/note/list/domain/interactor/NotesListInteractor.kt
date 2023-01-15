package io.rocketlab.screen.note.list.domain.interactor

import io.rocketlab.screen.note.list.data.repository.NotesListRepository
import io.rocketlab.storage.database.model.Note
import io.rocketlab.storage.database.model.NoteStatus
import kotlinx.coroutines.flow.Flow

class NotesListInteractor(
    private val notesListRepository: NotesListRepository
) {

    fun loadNotes(): Flow<List<Note>> {
        return notesListRepository.getNotes()
    }

    suspend fun deleteNote(id: Int) {
        notesListRepository.deleteNote(id)
    }

    suspend fun updateNoteStatus(noteId: Int, newStatus: NoteStatus) {
        notesListRepository.updateNoteStatusById(noteId, newStatus)
    }
}