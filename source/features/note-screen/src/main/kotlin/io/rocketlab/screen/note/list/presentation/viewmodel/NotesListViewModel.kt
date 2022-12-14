package io.rocketlab.screen.note.list.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Destination
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.screen.note.list.domain.interactor.NotesListInteractor
import io.rocketlab.screen.note.list.presentation.model.NoteModel
import io.rocketlab.screen.note.list.presentation.model.NotesListScreenState
import io.rocketlab.screen.note.list.presentation.viewmodel.mapper.NotesListMapper
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class NotesListViewModel(
    private val interactor: NotesListInteractor,
    private val mapper: NotesListMapper,
    private val navigator: Navigator
) : BaseViewModel() {

    val uiState = state<NotesListScreenState>(NotesListScreenState.Loading)
    val showDeleteNoteDialog = state<NoteModel?>(null)

    val onBackPressedAction = action<Unit> { onBackPressed() }
    val onAddNoteClickAction = action<Unit> { createNote() }
    val onNoteClickAction = action<NoteModel> { expandNote(it) }
    val onNoteEditClickAction = action<NoteModel> { openNoteEditor(it) }
    val onNoteDeleteClickAction = action<NoteModel> { showDeleteNoteDialog(it) }
    val onDismissDeleteDialogAction = action<Unit> { dismissDeleteDialog() }
    val deleteNoteAction = action<NoteModel> { deleteNote(it) }

    init {
        loadNotes()
    }

    private fun loadNotes() {
        launchJob {
            interactor.loadNotes()
                .map { mapper.map(it) }
                .collect(uiState)
        }
    }

    private fun onBackPressed() {
        navigator.navigateUp()
    }

    private fun createNote() {
        navigator.navigate(Destination.NoteEditor)
    }

    private fun expandNote(note: NoteModel) {
        uiState.update { state ->
            if (state.asContentOrNull() == null) return

            val mutableNotes = state.asContent().notes.toMutableList()
            val noteIndex = mutableNotes.indexOf(note)

            mutableNotes.forEachIndexed { index, note ->
                if (index == noteIndex) {
                    mutableNotes[index] = note.copy(isExpanded = note.isExpanded.not())
                }
            }

            NotesListScreenState.Content(mutableNotes)
        }
    }

    private fun showDeleteNoteDialog(note: NoteModel) {
        showDeleteNoteDialog.update { note }
    }

    private fun openNoteEditor(note: NoteModel) {
        val params = mapOf(
            Destination.NoteEditor.KEY_NOTE_ID to note.id
        )
        navigator.navigate(Destination.NoteEditor, params)
    }

    private fun deleteNote(note: NoteModel) {
        launchJob {
            interactor.deleteNote(note.id)
            dismissDeleteDialog()
        }
    }

    private fun dismissDeleteDialog() {
        showDeleteNoteDialog.update { null }
    }
}