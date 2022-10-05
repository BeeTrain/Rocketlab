package io.rocketlab.screen.note.presentation.list.presentation.view.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.rocketlab.screen.note.presentation.list.presentation.model.NoteModel
import io.rocketlab.ui.R
import io.rocketlab.ui.dialog.AppAlertDialog

@Composable
fun DeleteNoteDialog(
    note: NoteModel,
    onApply: (NoteModel) -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit
) {
    AppAlertDialog(
        message = stringResource(R.string.notes_screen_delete_dialog_message),
        confirmButton = stringResource(R.string.delete) to { onApply.invoke(note) },
        dismissButton = stringResource(R.string.cancel) to { onCancel.invoke() },
        onDismissRequest = { onDismiss.invoke() }
    )
}