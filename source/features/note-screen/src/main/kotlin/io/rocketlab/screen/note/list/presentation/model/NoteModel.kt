package io.rocketlab.screen.note.list.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class NoteModel(
    val id: Int,
    val text: String,
    val updatedOn: String,
    val isExpanded: Boolean = false
)