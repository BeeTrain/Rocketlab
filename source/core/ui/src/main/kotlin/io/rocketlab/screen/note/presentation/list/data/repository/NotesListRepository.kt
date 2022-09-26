package io.rocketlab.screen.note.presentation.list.data.repository

import io.rocketlab.screen.note.data.model.Note
import io.rocketlab.storage.database.dao.NoteDao
import kotlinx.coroutines.flow.Flow

class NotesListRepository(
    private val noteDao: NoteDao
) {

    fun getNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }
}