package io.rocketlab.screen.note.list.presentation.viewmodel.mapper

import io.rocketlab.screen.note.list.presentation.model.NoteModel
import io.rocketlab.storage.database.model.Note
import io.rocketlab.utils.extension.timeAgo

class NoteModelMapper {

    fun map(noteEntity: Note): NoteModel {
        return NoteModel(
            id = noteEntity.id,
            text = noteEntity.text,
            status = noteEntity.status,
            updatedOn = noteEntity.updatedOn.timeAgo()
        )
    }
}