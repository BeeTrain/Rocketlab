package io.rocketlab.screen.note.presentation.editor.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.Navigator
import io.rocketlab.screen.note.data.model.Note
import io.rocketlab.screen.note.presentation.editor.domain.interactor.NoteEditorInteractor
import io.rocketlab.screen.note.presentation.editor.presentation.model.NoteEditorScreenState
import kotlinx.coroutines.flow.update

class NoteEditorViewModel(
    private val noteId: Int?,
    private val interactor: NoteEditorInteractor,
    private val navigator: Navigator
) : BaseViewModel() {

    val noteEditorScreenState = state(NoteEditorScreenState())

    val onBackPressedAction = action<Unit> { onBackPressed() }
    val onCheckClickAction = action<Unit> { saveNote() }
    val onDeleteClickAction = action<Unit> { deleteNote() }

    val updateNoteTextAction = action<String> { updateNoteText(it) }

    private fun onBackPressed() {
        navigator.navigateUp()
    }

    private fun updateNoteText(newValue: String) {
        noteEditorScreenState.update { state ->
            state.copy(
                textField = state.textField.copy(value = newValue)
            )
        }
    }

    private fun saveNote() {
        launch {
            val text = noteEditorScreenState.value.textField.value
            interactor.saveNote(Note(text))
            navigator.navigateUp()
        }
    }

    private fun deleteNote() {
        navigator.navigateUp()
    }
}