package io.rocketlab.screen.note.presentation.list.data.repository

import io.rocketlab.storage.database.model.Note
import io.rocketlab.storage.database.dao.NoteDao
import kotlinx.coroutines.flow.Flow

class NotesListRepository(
    private val noteDao: NoteDao
) {

    fun getNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    suspend fun deleteNote(id: Int) {
        noteDao.deleteNoteById(id)
    }
}