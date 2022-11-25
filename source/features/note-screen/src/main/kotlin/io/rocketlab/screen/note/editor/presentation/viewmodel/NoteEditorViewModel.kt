package io.rocketlab.screen.note.editor.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Destination
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.screen.note.editor.domain.interactor.NoteEditorInteractor
import io.rocketlab.screen.note.editor.presentation.model.NoteEditorScreenState
import io.rocketlab.storage.database.model.Note
import io.rocketlab.utils.extension.isNotNull
import kotlinx.coroutines.flow.update

class NoteEditorViewModel(
    private val interactor: NoteEditorInteractor,
    private val navigator: Navigator
) : BaseViewModel() {

    private val noteId = navigator.parameter<Int?>(Destination.NoteEditor.KEY_NOTE_ID)

    val noteEditorScreenState = state(prepareNoteEditorScreenState())

    val onBackPressedAction = action<Unit> { onBackPressed() }
    val onCheckClickAction = action<Unit> { saveNote() }
    val onDeleteClickAction = action<Unit> { deleteNote() }

    val updateNoteTextAction = action<String> { updateNoteText(it) }

    private var note = Note()

    init {
        loadNote()
    }

    private fun loadNote() {
        launchJob {
            note = interactor.loadNote(noteId)
            updateNoteText(note.text)
        }
    }

    private fun onBackPressed() {
        navigator.navigateUp()
    }

    private fun updateNoteText(newValue: String) {
        noteEditorScreenState.update { state ->
            state.copy(
                textField = state.textField.copy(value = newValue),
                isSavingEnabled = newValue.isNotEmpty()
            )
        }
    }

    private fun saveNote() {
        launchJob {
            note.text = noteEditorScreenState.value.textField.value
            interactor.saveNote(note)
            navigator.navigateUp()
        }
    }

    private fun deleteNote() {
        launchJob {
            interactor.deleteNote(note.id)
            navigator.navigateUp()
        }
    }

    private fun prepareNoteEditorScreenState(): NoteEditorScreenState {
        return NoteEditorScreenState(
            isDeletingEnabled = noteId.isNotNull()
        )
    }
}