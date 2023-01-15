package io.rocketlab.screen.note.list.data.repository

import io.rocketlab.storage.database.dao.NoteDao
import io.rocketlab.storage.database.model.Note
import io.rocketlab.storage.database.model.NoteStatus
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

   suspend fun updateNoteStatusById(id: Int, newStatus: NoteStatus) {
        noteDao.updateStatusById(id, newStatus)
    }
}