package io.rocketlab.screen.note.list.presentation.viewmodel.mapper

import io.rocketlab.screen.note.list.presentation.model.NotesListScreenState
import io.rocketlab.storage.database.model.Note
import io.rocketlab.storage.database.model.NoteStatus

class NotesListContentMapper(
    private val notesColumnMapper: NotesColumnMapper
) {

    fun map(notes: List<Note>): NotesListScreenState.Content {
        return NotesListScreenState.Content(
            todoNotes = notesColumnMapper.map(notes, NoteStatus.TODO),
            doneNotes = notesColumnMapper.map(notes, NoteStatus.DONE)
        )
    }
}