package com.raytalktech.printcompose.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raytalktech.printcompose.config.ViewModelFactory
import com.raytalktech.printcompose.data.UiState
import com.raytalktech.printcompose.di.Injection
import com.raytalktech.printcompose.model.data.ReceiverOrder
import com.raytalktech.printcompose.model.data.dummyPrintingData
import com.raytalktech.printcompose.ui.component.PrintingItems
import com.raytalktech.printcompose.ui.theme.PrintComposeTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(
                LocalContext.current
            )
        )
    ),
    navigate: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> viewModel.getReceiverData()
            is UiState.Success -> HomeContent(uiState.data, modifier, navigate)
            is UiState.Error -> {}
        }
    }
}

@Composable
fun ReceiverOrderRows(
    listPrinting: List<ReceiverOrder>,
    modifier: Modifier,
    navigate: (Long) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        items(listPrinting) { data ->
            PrintingItems(
                receiverOrder = data,
                modifier = modifier.clickable { navigate(data.orderID ?: 1L) })
        }
    }
}

@Composable
fun HomeContent(
    listPrinting: List<ReceiverOrder>,
    modifier: Modifier,
    navigate: (Long) -> Unit
) {
    Column {
        ReceiverOrderRows(listPrinting = listPrinting, modifier = modifier, navigate = navigate)
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun HomePreview() {
    PrintComposeTheme {
        HomeContent(
            listPrinting = dummyPrintingData,
            modifier = Modifier.padding(8.dp),
            navigate = {})
    }
}

