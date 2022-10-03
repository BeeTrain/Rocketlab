package io.rocketlab.screen.note.presentation.list.presentation.model

data class NoteModel(
    val id: Int,
    val text: String,
    val updatedOn: String,
    val isExpanded: Boolean = false
)