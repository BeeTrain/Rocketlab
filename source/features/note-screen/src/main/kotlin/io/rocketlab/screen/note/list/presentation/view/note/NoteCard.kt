package io.rocketlab.screen.note.list.presentation.view.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.rocketlab.screen.note.list.presentation.model.NoteModel
import io.rocketlab.ui.shape.WavedShape

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: NoteModel,
    onNoteClick: (NoteModel) -> Unit
) {
    Card(
        shape = WavedShape(),
        modifier = modifier
            .defaultMinSize(minHeight = 128.dp)
            .clickable { onNoteClick.invoke(note) }
    ) {
        NoteText(note)
        NoteDate(note)
    }
}

@Composable
fun ExpandedNoteCard(
    expandedNoteCardState: State<NoteModel?>,
    onDismiss: (NoteModel) -> Unit,
    onEdit: (NoteModel) -> Unit,
    onDelete: (NoteModel) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val cardWidth = (configuration.screenWidthDp * 0.8).dp
    val cardHeight = (configuration.screenHeightDp * 0.6).dp

    expandedNoteCardState.value?.let { note ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Dialog(onDismissRequest = { onDismiss.invoke(note) }) {
                Card(
                    shape = WavedShape(10),
                    modifier = Modifier
                        .size(cardWidth, cardHeight)
                        .align(Alignment.Center)
                ) {
                    NoteText(note, isExpanded = true)
                    Row(
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        NoteMenu(note, onEdit, onDelete)
                        NoteDate(
                            note = note,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.Bottom)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnScope.NoteText(
    note: NoteModel,
    isExpanded: Boolean = false
) {
    Text(
        text = note.text,
        style = MaterialTheme.typography.titleMedium,
        maxLines = if (isExpanded) Int.MAX_VALUE else 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
    )
}

@Composable
fun NoteDate(
    note: NoteModel,
    modifier: Modifier = Modifier
) {
    Text(
        text = note.updatedOn,
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun NoteMenu(
    note: NoteModel,
    onEdit: (NoteModel) -> Unit,
    onDelete: (NoteModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { onEdit.invoke(note) },
            imageVector = Icons.Filled.Edit,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { onDelete.invoke(note) },
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}