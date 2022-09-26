package io.rocketlab.screen.note.presentation.editor.presentation.model

import io.rocketlab.screen.note.presentation.editor.presentation.view.text.NoteTextFieldState

data class NoteEditorScreenState(
    val textField: NoteTextFieldState = NoteTextFieldState()
)