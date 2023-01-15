package io.rocketlab.screen.note.list.presentation.viewmodel.mapper

import io.rocketlab.screen.note.list.presentation.model.NoteColumnModel
import io.rocketlab.screen.note.list.presentation.model.NoteModel
import io.rocketlab.storage.database.model.Note
import io.rocketlab.storage.database.model.NoteStatus

class NotesColumnMapper(
    private val noteColumnTitleMapper: NoteColumnTitleMapper,
    private val noteModelMapper: NoteModelMapper
) {

    fun map(notes: List<Note>, noteStatus: NoteStatus): NoteColumnModel {
        return NoteColumnModel(
            title = noteColumnTitleMapper.map(noteStatus),
            notes = mapNotes(notes, noteStatus)
        )
    }

    private fun mapNotes(notes: List<Note>, status: NoteStatus): List<NoteModel> {
        return notes.map { noteModelMapper.map(it) }
            .filter { it.status == status }
    }
}