package io.rocketlab.screen.note.presentation.list.presentation.view.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.rocketlab.screen.note.presentation.list.presentation.model.NoteModel
import io.rocketlab.ui.theme.Colors

@Composable
fun LazyItemScope.NoteCard(
    note: NoteModel,
    onCardClick: (NoteModel) -> Unit,
    onEditClick: (NoteModel) -> Unit,
    onDeleteClick: (NoteModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .clickable { onCardClick.invoke(note) },
        content = {
            NoteMenu(note, onEditClick, onDeleteClick)
            NoteText(note)
            NoteDate(note)
        }
    )
}

@Composable
fun ColumnScope.NoteMenu(
    note: NoteModel,
    onEditClick: (NoteModel) -> Unit,
    onDeleteClick: (NoteModel) -> Unit,
) {
    if (note.isExpanded) {
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(vertical = 4.dp, horizontal = 16.dp)
        ) {
            NoteMenuIcon(
                color = Colors.warning,
                onClick = { onEditClick.invoke(note) }
            )
            Spacer(modifier = Modifier.width(4.dp))
            NoteMenuIcon(
                color = Colors.negative,
                onClick = { onDeleteClick.invoke(note) }
            )
        }
    } else {
        Spacer(modifier = Modifier.size(0.dp))
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
            .padding(
                top = if (note.isExpanded) 0.dp else 4.dp,
                bottom = 4.dp,
                start = 16.dp,
                end = 16.dp
            )
    )
}

@Composable
fun ColumnScope.NoteDate(note: NoteModel) {
    Text(
        text = note.updatedOn,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .padding(vertical = 2.dp, horizontal = 16.dp)
            .align(Alignment.End)
    )
}

@Composable
fun NoteDivider(isLastItem: Boolean) {
    if (isLastItem) {
        Spacer(modifier = Modifier.height(88.dp))
    } else {
        Divider(
            modifier = Modifier.padding(horizontal = 14.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
        )
    }
}

@Composable
fun NoteMenuIcon(
    color: Color,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .size(16.dp),
        content = {
            Icon(
                imageVector = Icons.Filled.Circle,
                tint = color,
                contentDescription = null,
            )
        },
        onClick = onClick
    )
}