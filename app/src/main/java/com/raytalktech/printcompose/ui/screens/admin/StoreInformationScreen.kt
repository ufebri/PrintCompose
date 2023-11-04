package com.raytalktech.printcompose.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raytalktech.printcompose.config.ViewModelFactory
import com.raytalktech.printcompose.data.UiState
import com.raytalktech.printcompose.di.Injection
import com.raytalktech.printcompose.model.ui.textFieldItemNewOrder
import com.raytalktech.printcompose.model.ui.textFieldStoreInformation
import com.raytalktech.printcompose.ui.component.CenteredAppBar
import com.raytalktech.printcompose.ui.screens.neworder.TextFieldRow
import com.raytalktech.printcompose.ui.theme.PrintComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreInformationScreen(
    modifier: Modifier = Modifier,
    navigationBack: () -> Unit,
    onSaveClicked: () -> Unit,
    viewModel: AdminViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(
                LocalContext.current
            )
        )
    ),
) {
    val editTextValues = remember {
        mutableStateListOf(*Array(textFieldItemNewOrder.size) { TextFieldValue() })
    }

    Scaffold(modifier = modifier,
        topBar = {
            CenteredAppBar(
                title = "Store Information",
                icNav = Icons.Default.ArrowBack,
                onClickNav = navigationBack,
                icAction = Icons.Default.CheckCircle,
                onClickAction = {
                    viewModel.createStoreData(
                        nameBrand = editTextValues[0].text,
                        addressBrand = editTextValues[1].text,
                        telpBrand = editTextValues[2].text,
                        emailBrand = editTextValues[3].text
                    )
                    onSaveClicked()
                }
            )
        }) { contentPadding ->
        Column(modifier = modifier.padding(contentPadding)) {
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> viewModel.getStoreData()
                    is UiState.Success -> {
                        val listTextValue = if (uiState.data.isNotEmpty()) {
                            listOf(
                                TextFieldValue(text = uiState.data[0].nameBrand ?: ""),
                                TextFieldValue(text = uiState.data[0].addressBrand ?: ""),
                                TextFieldValue(text = uiState.data[0].telpBrand ?: ""),
                                TextFieldValue(text = uiState.data[0].emailBrand ?: "")
                            )
                        } else {
                            editTextValues
                        }

                        TextFieldRow(
                            textFieldLabels = textFieldStoreInformation,
                            editTextValues = listTextValue,
                            onEditTextValueChanged = { updatedValues ->
                                editTextValues.clear()
                                editTextValues.addAll(updatedValues)
                            }
                        )
                    }

                    is UiState.Error -> {}
                }
            }
        }
    }
}


@Composable
@Preview
fun StoreInformationPreview() {
    PrintComposeTheme {
        StoreInformationScreen(navigationBack = {}, onSaveClicked = {})
    }
}