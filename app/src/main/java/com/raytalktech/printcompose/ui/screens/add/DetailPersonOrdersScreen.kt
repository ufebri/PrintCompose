package com.raytalktech.printcompose.ui.screens.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raytalktech.printcompose.R
import com.raytalktech.printcompose.config.ViewModelFactory
import com.raytalktech.printcompose.data.UiState
import com.raytalktech.printcompose.di.Injection
import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.ui.component.CenteredAppBar
import com.raytalktech.printcompose.ui.component.FabAddOrderItem
import com.raytalktech.printcompose.ui.component.OrderItem
import com.raytalktech.printcompose.ui.theme.PrintComposeTheme


@Composable
fun DetailPersonOrdersScreen(
    modifier: Modifier = Modifier,
    idReceiverOrder: Long,
    viewModel: DetailPersonOrdersViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToCreateNewOrder: (Long) -> Unit,
    onBackClicked: () -> Unit,
    onPrintClicked: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getListOrderItems(idReceiverOrder)
            }

            is UiState.Success -> {
                AddItemContent(
                    listOrder = uiState.data,
                    modifier = modifier,
                    navigateToCreateNewOrderItem = navigateToCreateNewOrder,
                    idReceiverOrder = idReceiverOrder,
                    onBackClicked = onBackClicked,
                    onPrintClicked = onPrintClicked
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemContent(
    idReceiverOrder: Long,
    listOrder: List<ReceiverDetailOrder>,
    modifier: Modifier,
    navigateToCreateNewOrderItem: (Long) -> Unit,
    onBackClicked: () -> Unit,
    onPrintClicked: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FabAddOrderItem(onClick = {
                navigateToCreateNewOrderItem(
                    idReceiverOrder
                )
            })
        },
        topBar = {
            CenteredAppBar(
                title = "Order List",
                icNav = Icons.Default.ArrowBack,
                onClickNav = { onBackClicked() },
                icAction = ImageVector.vectorResource(R.drawable.ic_print),
                onClickAction = { onPrintClicked() }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OrderItemsRow(
                listOrder = listOrder,
                modifier = modifier
            )
        }
    }
}


@Composable
fun OrderItemsRow(
    listOrder: List<ReceiverDetailOrder>,
    modifier: Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        items(listOrder) { data ->
            OrderItem(
                receiverDetailOrder = data,
                modifier = modifier)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddItemScreenPreview() {
    PrintComposeTheme {

    }
}