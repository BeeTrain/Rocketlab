package io.rocketlab.screen.note.presentation.list.presentation.view.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.rocketlab.screen.note.data.model.Note

@Composable
fun NoteCard(
    note: Note,
    onClick: (Note) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .clickable { onClick.invoke(note) },
        content = {
            NoteTitle(note = note)
        }
    )
}

@Composable
fun NoteTitle(note: Note) {
    Text(
        text = note.text,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
    )
}

@Composable
fun NoteDivider(isLastItem: Boolean) {
    if (isLastItem) {
        Spacer(modifier = Modifier.height(8.dp))
    } else {
        Divider(
            modifier = Modifier.padding(horizontal = 14.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
        )
    }
}