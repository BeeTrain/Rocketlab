package io.rocketlab.screen.note.list.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface NotesListScreenState {

    @Immutable
    object Loading : NotesListScreenState

    @Immutable
    data class Content(
        val notes: List<NoteModel> = emptyList()
    ) : NotesListScreenState

    fun asContent() = this as Content

    fun asContentOrNull() = this as? Content
}