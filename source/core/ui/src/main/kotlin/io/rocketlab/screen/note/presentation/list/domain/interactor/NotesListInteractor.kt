package io.rocketlab.screen.note.presentation.list.domain.interactor

import io.rocketlab.screen.note.data.model.Note
import io.rocketlab.screen.note.presentation.list.data.repository.NotesListRepository
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
}