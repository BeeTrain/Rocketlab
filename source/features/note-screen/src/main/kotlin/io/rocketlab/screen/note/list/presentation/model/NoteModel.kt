package io.rocketlab.screen.note.list.presentation.model

import androidx.compose.runtime.Immutable
import io.rocketlab.storage.database.model.NoteStatus

@Immutable
data class NoteModel(
    val id: Int,
    val text: String,
    val status: NoteStatus,
    val updatedOn: String,
    val isExpanded: Boolean = false
)