package io.rocketlab.screen.note.list.presentation.model

data class NoteColumnModel(
    val title: String,
    val notes: List<NoteModel>
) {

    fun isEmpty(): Boolean {
        return notes.isEmpty()
    }
}