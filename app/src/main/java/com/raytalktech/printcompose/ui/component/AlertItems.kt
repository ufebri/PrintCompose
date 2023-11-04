package com.raytalktech.printcompose.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.raytalktech.printcompose.ui.theme.PrintComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertItems(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    if (showDialog) {
        var text by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Receiver Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm(text)
                        onDismiss()
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun AlertPreview() {
    PrintComposeTheme {
        var showDialog by remember { mutableStateOf(true) }
        var enteredText by remember { mutableStateOf("") }

        AlertItems(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = { text ->
                enteredText = text
                showDialog = false
            })
    }
}