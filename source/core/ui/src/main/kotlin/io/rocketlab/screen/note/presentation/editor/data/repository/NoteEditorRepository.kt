package io.rocketlab.screen.note.presentation.editor.data.repository

import io.rocketlab.screen.note.data.model.Note
import io.rocketlab.storage.database.dao.NoteDao

class NoteEditorRepository(
    private val noteDao: NoteDao
) {

    suspend fun getNote(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun deleteNote(id: Int) {
        noteDao.deleteNoteById(id)
    }
}