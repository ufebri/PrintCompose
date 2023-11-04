package com.raytalktech.printcompose.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldItems(
    label: String,
    keyboardType: KeyboardType,
    value: TextFieldValue,
    isError: Boolean? = null,
    errorMessage: String? = null,
    onValueChanged: (TextFieldValue) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        isError = isError ?: false,
        placeholder = { Text(text = errorMessage ?: "") }
    )
}