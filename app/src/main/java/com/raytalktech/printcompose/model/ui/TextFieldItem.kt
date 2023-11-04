package com.raytalktech.printcompose.model.ui

import androidx.compose.ui.text.input.KeyboardType

data class TextFieldItem(
    val labelText: String,
    val keyboardType: KeyboardType,
    val isRequired: Boolean,
    val errorText: String? = null
)

val textFieldItemNewOrder = listOf(
    TextFieldItem("Item Name", KeyboardType.Text, true, "Required Field"),
    TextFieldItem("Item Unit", KeyboardType.Text, false),
    TextFieldItem("Item Quantity", KeyboardType.Number, true, "Required Field"),
    TextFieldItem("Item Price", KeyboardType.Number, true, "Required Field")
)

val textFieldStoreInformation = listOf(
    TextFieldItem("Store Name", KeyboardType.Text, true),
    TextFieldItem("Address", KeyboardType.Text, true),
    TextFieldItem("Phone", KeyboardType.Phone, true),
    TextFieldItem("Email", KeyboardType.Email, true)
)