package io.rocketlab.screen.note.presentation.list.presentation.viewmodel.mapper

import io.rocketlab.screen.note.data.model.Note
import io.rocketlab.screen.note.presentation.list.presentation.model.NoteModel
import io.rocketlab.utils.extension.timeAgo

class NotesListMapper {

    fun map(notes: List<Note>): List<NoteModel> {
        return notes.map { noteEntity ->
            NoteModel(
                id = noteEntity.id,
                text = noteEntity.text,
                updatedOn = noteEntity.updatedOn.timeAgo()
            )
        }
    }
}