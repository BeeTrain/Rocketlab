package io.rocketlab.screen.note.presentation.list.presentation.viewmodel

import androidx.core.os.bundleOf
import io.rocketlab.arch.extension.action
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.Destination
import io.rocketlab.navigation.Navigator
import io.rocketlab.screen.note.data.model.Note
import io.rocketlab.screen.note.presentation.list.domain.interactor.NotesListInteractor
import kotlinx.coroutines.flow.MutableStateFlow

class NotesListViewModel(
    private val interactor: NotesListInteractor,
    private val navigator: Navigator
) : BaseViewModel() {

    val notesState = MutableStateFlow<List<Note>>(emptyList())

    val onBackPressedAction = action<Unit> { onBackPressed() }
    val onAddNoteClickAction = action<Unit> { createNote() }
    val openNoteAction = action<Note> { openNote(it) }

    init {
        loadNotes()
    }

    private fun loadNotes() {
        launch {
            interactor.loadNotes().collect(notesState)
        }
    }

    private fun onBackPressed() {
        navigator.navigateUp()
    }

    private fun createNote() {
        navigator.navigate(Destination.NoteEditor)
    }

    private fun openNote(note: Note) {
        val params = bundleOf(
            Destination.NoteEditor.KEY_NOTE_ID to note.id
        )
        navigator.navigate(Destination.NoteEditor)
    }
}