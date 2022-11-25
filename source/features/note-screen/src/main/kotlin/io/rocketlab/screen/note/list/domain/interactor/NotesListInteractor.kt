package io.rocketlab.screen.note.list.domain.interactor

import io.rocketlab.screen.note.list.data.repository.NotesListRepository
import kotlinx.coroutines.flow.Flow

class NotesListInteractor(
    private val notesListRepository: NotesListRepository
) {

    fun loadNotes(): Flow<List<io.rocketlab.storage.database.model.Note>> {
        return notesListRepository.getNotes()
    }

    suspend fun deleteNote(id: Int) {
        notesListRepository.deleteNote(id)
    }
}