package io.rocketlab.screen.note.list.presentation.model

import io.rocketlab.storage.database.model.NoteStatus

data class UpdateNoteStatusAction(
    val note: NoteModel,
    val status: NoteStatus
)