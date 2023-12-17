package com.puzzle_agency.androidknowledge.knowledge.ui.view.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    title: String,
    description: String,
    properties: DialogProperties = DialogProperties(),
    shape: Shape = RoundedCornerShape(12.dp),
    onDismissRequest: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column(
            modifier = Modifier
                .clip(shape)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = {},
                modifier = Modifier.align(Alignment.End),
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}