package io.rocketlab.screen.note.presentation.editor.data.repository

import io.rocketlab.screen.note.data.model.Note
import io.rocketlab.storage.database.dao.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NoteEditorRepository(
    private val noteDao: NoteDao
) {

    fun getNote(id: Int?): Flow<Note> {
        return id?.let { noteDao.getNoteById(it) } ?: flowOf(Note())
    }

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }
}