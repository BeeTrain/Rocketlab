package io.rocketlab.screen.note.editor.data.repository

import io.rocketlab.storage.database.model.Note
import io.rocketlab.storage.database.dao.NoteDao

class NoteEditorRepository(
    private val noteDao: NoteDao
) {

    suspend fun getNote(id: Int?): Note {
        return when (id) {
            null -> getNewNote()
            else -> noteDao.getNoteById(id) ?: getNewNote()
        }
    }

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }

    suspend fun deleteNote(id: Int) {
        noteDao.deleteNoteById(id)
    }

    private suspend fun getNewNote(): Note {
        return Note(id = noteDao.getNewNoteId())
    }
}