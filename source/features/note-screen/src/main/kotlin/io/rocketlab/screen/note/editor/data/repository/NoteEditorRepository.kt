package io.rocketlab.screen.note.editor.data.repository

import io.rocketlab.storage.database.model.Note
import io.rocketlab.storage.database.dao.NoteDao

class NoteEditorRepository(
    private val noteDao: io.rocketlab.storage.database.dao.NoteDao
) {

    suspend fun getNote(id: Int?): io.rocketlab.storage.database.model.Note {
        return when (id) {
            null -> getNewNote()
            else -> noteDao.getNoteById(id) ?: getNewNote()
        }
    }

    suspend fun addNote(note: io.rocketlab.storage.database.model.Note) {
        noteDao.insert(note)
    }

    suspend fun deleteNote(id: Int) {
        noteDao.deleteNoteById(id)
    }

    private suspend fun getNewNote(): io.rocketlab.storage.database.model.Note {
        return io.rocketlab.storage.database.model.Note(id = noteDao.getNewNoteId())
    }
}