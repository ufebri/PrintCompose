package com.raytalktech.printcompose.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raytalktech.printcompose.config.ViewModelFactory
import com.raytalktech.printcompose.di.Injection
import com.raytalktech.printcompose.ui.screens.home.HomeViewModel


@Composable
fun FabAddOrderItem(onClick: () -> Unit) {
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxWidth()) {
        ExtendedFloatingActionButton(
            onClick = { onClick() },
            icon = { Icon(Icons.Filled.Add, "ini testing") },
            text = { Text(text = "Add Order Item") },
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Composable
fun FabAddReceiverOrder(
    vm: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current)
        )
    )
) {
    var showDialog by remember { mutableStateOf(false) }
    var enteredText by remember { mutableStateOf("") }

    FloatingActionButton(
        onClick = {
            showDialog = true
        },
        modifier = Modifier
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
    }


    if (showDialog) {
        AlertItems(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = { text ->
                enteredText = text
                vm.createReceiverOrder(enteredText)
                showDialog = false
            }
        )
    }
}