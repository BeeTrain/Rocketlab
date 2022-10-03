package io.rocketlab.screen.note.presentation.list.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.Destination
import io.rocketlab.navigation.Navigator
import io.rocketlab.screen.note.presentation.list.domain.interactor.NotesListInteractor
import io.rocketlab.screen.note.presentation.list.presentation.model.NoteModel
import io.rocketlab.screen.note.presentation.list.presentation.viewmodel.mapper.NotesListMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class NotesListViewModel(
    private val interactor: NotesListInteractor,
    private val mapper: NotesListMapper,
    private val navigator: Navigator
) : BaseViewModel() {

    val notesState = MutableStateFlow<List<NoteModel>>(emptyList())

    val onBackPressedAction = action<Unit> { onBackPressed() }
    val onAddNoteClickAction = action<Unit> { createNote() }
    val onNoteClickAction = action<NoteModel> { expandNote(it) }
    val onNoteEditAction = action<NoteModel> { openNoteEditor(it) }
    val onNoteDeleteAction = action<NoteModel> { deleteNote(it) }

    init {
        loadNotes()
    }

    private fun loadNotes() {
        launchJob {
            interactor.loadNotes()
                .map { mapper.map(it) }
                .collect(notesState)
        }
    }

    private fun onBackPressed() {
        navigator.navigateUp()
    }

    private fun createNote() {
        navigator.navigate(Destination.NoteEditor)
    }

    private fun expandNote(note: NoteModel) {
        notesState.update { notes ->
            val mutableNotes = notes.toMutableList()
            val noteIndex = mutableNotes.indexOf(note)



            notes.forEachIndexed { index, note ->
                if (index == noteIndex) {
                    mutableNotes[index] = note.copy(isExpanded = note.isExpanded.not())
                }
            }

            mutableNotes
        }
    }

    private fun openNoteEditor(note: NoteModel) {
        val params = mapOf(
            Destination.NoteEditor.KEY_NOTE_ID to note.id
        )
        navigator.navigate(Destination.NoteEditor, params)
    }

    private fun deleteNote(note: NoteModel) {
        launchJob { interactor.deleteNote(note.id) }
    }
}