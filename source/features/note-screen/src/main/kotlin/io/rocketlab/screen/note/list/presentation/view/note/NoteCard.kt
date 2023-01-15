package io.rocketlab.screen.note.list.presentation.view.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
fun ColumnScope.NoteText(note: NoteModel) {
    Text(
        text = note.text,
        style = MaterialTheme.typography.titleMedium,
        maxLines = if (note.isExpanded) Int.MAX_VALUE else 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
    )
}

@Composable
fun NoteDate(note: NoteModel) {
    Text(
        text = note.updatedOn,
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}