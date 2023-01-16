package io.rocketlab.screen.note.list.presentation.view.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.rocketlab.screen.note.R
import io.rocketlab.screen.note.list.presentation.model.NoteModel
import io.rocketlab.ui.dialog.AppAlertDialog
import io.rocketlab.ui.R as uiR

@Composable
fun DeleteNoteDialog(
    note: NoteModel,
    onApply: (NoteModel) -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit
) {
    AppAlertDialog(
        message = stringResource(R.string.notes_screen_delete_dialog_message),
        confirmButton = stringResource(uiR.string.delete) to { onApply.invoke(note) },
        dismissButton = stringResource(uiR.string.cancel) to { onCancel.invoke() },
        onDismissRequest = { onDismiss.invoke() }
    )
}