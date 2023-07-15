package io.rocketlab.screen.note.list.presentation.view.note

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.rocketlab.screen.note.list.presentation.model.NoteModel
import io.rocketlab.ui.draganddrop.DragTarget
import io.rocketlab.ui.draganddrop.DropTarget
import io.rocketlab.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesColumn(
    modifier: Modifier = Modifier,
    title: String,
    notes: List<NoteModel> = emptyList(),
    scrollState: LazyListState = rememberLazyListState(),
    onNoteClick: (NoteModel) -> Unit,
    onNoteDropped: (NoteModel) -> Unit
) {
    DropTarget<NoteModel>(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.5f)
    ) { isInBound, droppedNote ->
        droppedNote?.let { onNoteDropped.invoke(it) }

        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(notesColumnColor(isInBound))
        ) {
            stickyHeader {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Typography.headlineMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                )
            }
            items(notes) { note ->
                DragTarget(
                    modifier = Modifier
                        .align(Alignment.Center),
                    dataToDrop = note
                ) {
                    NoteCard(
                        modifier = Modifier.size(156.dp),
                        note = note,
                        onNoteClick = onNoteClick
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(88.dp)) }
        }
    }
}

private fun notesColumnColor(isInBound: Boolean): Color {
    return if (isInBound) Color.Black.copy(alpha = 0.1f) else Color.Transparent
}