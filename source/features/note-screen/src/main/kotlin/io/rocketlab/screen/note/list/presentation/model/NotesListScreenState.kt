package io.rocketlab.screen.note.list.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface NotesListScreenState {

    @Immutable
    data object Loading : NotesListScreenState

    @Immutable
    data class Content(
        val todoNotes: NoteColumnModel,
        val doneNotes: NoteColumnModel
    ) : NotesListScreenState

    fun asContent() = this as Content

    fun asContentOrNull() = this as? Content
}