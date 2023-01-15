package io.rocketlab.screen.note.list.presentation.viewmodel.mapper

import io.rocketlab.screen.note.R
import io.rocketlab.storage.database.model.NoteStatus
import io.rocketlab.utils.provider.resources.ResourcesProvider

class NoteColumnTitleMapper(
    private val resourcesProvider: ResourcesProvider
) {

    fun map(noteStatus: NoteStatus): String {
        return when (noteStatus) {
            NoteStatus.TODO -> resourcesProvider.getString(R.string.notes_list_todo_column_title)
            NoteStatus.DONE -> resourcesProvider.getString(R.string.notes_list_done_column_title)
        }
    }
}