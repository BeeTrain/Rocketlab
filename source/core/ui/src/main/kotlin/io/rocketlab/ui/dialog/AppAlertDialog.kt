package io.rocketlab.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppAlertDialog(
    title: String? = null,
    message: String? = null,
    confirmButton: Pair<String, () -> Unit>,
    dismissButton: Pair<String, () -> Unit>? = null,
    onDismissRequest: () -> Unit = {}
) {
    AlertDialog(
        modifier = Modifier.padding(20.dp),
        onDismissRequest = onDismissRequest,
        shape = MaterialTheme.shapes.extraLarge,
        title = { AppAlertDialogTitle(title) },
        text = { AppAlertDialogMessage(message) },
        confirmButton = {
            AlertDialogButton(
                button = confirmButton,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp)

            )
        },
        dismissButton = {
            AlertDialogButton(
                button = dismissButton,
                modifier = Modifier.padding(top = 8.dp, end = 8.dp)
            )
        }
    )
}

@Composable
fun AlertDialogButton(
    button: Pair<String, () -> Unit>?,
    modifier: Modifier = Modifier
) {
    if (button != null) {
        Text(
            text = button.first,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.clickable { button.second.invoke() }
        )
    } else {
        Spacer(modifier = Modifier.size(0.dp))
    }

}

@Composable
fun AppAlertDialogTitle(title: String?) {
    if (title != null) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

    } else {
        Spacer(modifier = Modifier.size(0.dp))
    }
}

@Composable
fun AppAlertDialogMessage(message: String?) {
    if (message != null) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )

    } else {
        Spacer(modifier = Modifier.size(0.dp))
    }
}