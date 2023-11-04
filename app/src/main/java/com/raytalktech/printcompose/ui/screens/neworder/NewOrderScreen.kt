package com.raytalktech.printcompose.ui.screens.neworder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raytalktech.printcompose.config.ViewModelFactory
import com.raytalktech.printcompose.di.Injection
import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.model.ui.TextFieldItem
import com.raytalktech.printcompose.model.ui.textFieldItemNewOrder
import com.raytalktech.printcompose.ui.component.TextFieldItems
import com.raytalktech.printcompose.ui.component.TopBarDefault
import com.raytalktech.printcompose.ui.theme.PrintComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderScreen(
    vm: NewOrderViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current)
        )
    ), idReceiverOrder: Long, onBackClick: () -> Unit, onSaveClicked: () -> Unit
) {
    val editTextValues = remember {
        mutableStateListOf(*Array(textFieldItemNewOrder.size) { TextFieldValue() })
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopBarDefault(
            title = "New Order",
            navigationClick = { onBackClick() },
            icon = Icons.Default.ArrowBack
        )
    }) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
//            if (idOrder != -1L) {
//                editTextValues
//            } else {
//
//            }


            NewOrderContent(textFieldLabels = textFieldItemNewOrder,
                editTextValues = editTextValues,
                onEditTextValueChanged = { updatedValues ->
                    editTextValues.clear()
                    editTextValues.addAll(updatedValues)
                },
                onSaveClicked = {
                    vm.createNewOrder(
                        ReceiverDetailOrder(
                            idReceiverOrder = idReceiverOrder,
                            nameItemOrder = editTextValues[0].text,
                            unitItemOrder = editTextValues[1].text,
                            quantityItemOrder = Integer.parseInt(editTextValues[2].text),
                            priceItemOrder = Integer.parseInt(editTextValues[3].text),
                            amountItemOrder = Integer.parseInt(editTextValues[2].text) * Integer.parseInt(
                                editTextValues[3].text
                            )
                        )
                    )
                    onSaveClicked()
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderContent(
    modifier: Modifier = Modifier,
    textFieldLabels: List<TextFieldItem>,
    editTextValues: List<TextFieldValue>,
    onEditTextValueChanged: (List<TextFieldValue>) -> Unit,
    onSaveClicked: () -> Unit
) {

    val amountItem =
        if (editTextValues[2].text.isNotEmpty() && editTextValues[3].text.isNotEmpty()) Integer.parseInt(
            editTextValues[2].text
        ) * Integer.parseInt(editTextValues[3].text)
        else ""
    var amounts = "$amountItem"

    Column(modifier = modifier.padding(8.dp)) {
        TextFieldRow(
            textFieldLabels = textFieldLabels,
            editTextValues = editTextValues,
            onEditTextValueChanged = onEditTextValueChanged
        )
        OutlinedTextField(modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
            value = amounts,
            onValueChange = { amounts = it },
            enabled = false,
            label = { Text(text = "Total Amount") })
        Button(onClick = onSaveClicked, modifier = modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Save")
        }
    }
}

@Composable
fun TextFieldRow(
    textFieldLabels: List<TextFieldItem>,
    editTextValues: List<TextFieldValue>,
    onEditTextValueChanged: (List<TextFieldValue>) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(textFieldLabels) { textLabel ->
            val index = textFieldLabels.indexOf(textLabel)
            TextFieldItems(
                label = textLabel.labelText,
                keyboardType = textLabel.keyboardType,
                value = editTextValues[index]
            ) { newValue ->
                val updatedValues = editTextValues.toMutableList().also {
                    it[index] = newValue
                }
                onEditTextValueChanged(updatedValues)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewNewOrder() {
    PrintComposeTheme {
        NewOrderScreen(idReceiverOrder = 1L, onSaveClicked = {}, onBackClick = {})
    }
}