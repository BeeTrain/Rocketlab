package io.rocketlab.screen.note.list.presentation.viewmodel.mapper

import io.rocketlab.screen.note.list.presentation.model.NoteModel
import io.rocketlab.screen.note.list.presentation.model.NotesListScreenState
import io.rocketlab.utils.extension.timeAgo

class NotesListMapper {

    fun map(notes: List<io.rocketlab.storage.database.model.Note>): NotesListScreenState.Content {
        return NotesListScreenState.Content(
            notes = mapNotes(notes)
        )
    }

    private fun mapNotes(notes: List<io.rocketlab.storage.database.model.Note>): List<NoteModel> {
        return notes.map { noteEntity ->
            NoteModel(
                id = noteEntity.id,
                text = noteEntity.text,
                updatedOn = noteEntity.updatedOn.timeAgo()
            )
        }
    }
}