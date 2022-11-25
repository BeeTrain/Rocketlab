package io.rocketlab.screen.note.editor.presentation.model

import androidx.compose.runtime.Immutable
import io.rocketlab.screen.note.editor.presentation.view.text.NoteTextFieldState

@Immutable
data class NoteEditorScreenState(
    val textField: NoteTextFieldState = NoteTextFieldState(),
    val isSavingEnabled: Boolean = false,
    val isDeletingEnabled: Boolean = false
)