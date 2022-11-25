package io.rocketlab.screen.note.list.data.repository

import io.rocketlab.storage.database.model.Note
import io.rocketlab.storage.database.dao.NoteDao
import kotlinx.coroutines.flow.Flow

class NotesListRepository(
    private val noteDao: io.rocketlab.storage.database.dao.NoteDao
) {

    fun getNotes(): Flow<List<io.rocketlab.storage.database.model.Note>> {
        return noteDao.getAllNotes()
    }

    suspend fun deleteNote(id: Int) {
        noteDao.deleteNoteById(id)
    }
}