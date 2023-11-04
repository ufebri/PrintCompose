package com.raytalktech.printcompose.ui.screens.print

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raytalktech.printcompose.config.ViewModelFactory
import com.raytalktech.printcompose.data.UiState
import com.raytalktech.printcompose.di.Injection
import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.model.data.StoreData
import com.raytalktech.printcompose.ui.screens.add.DetailPersonOrdersViewModel
import com.raytalktech.printcompose.ui.screens.admin.AdminViewModel
import com.raytalktech.printcompose.util.BluetoothPrinterHelper
import com.raytalktech.printcompose.util.BluetoothTextBuilder

@SuppressLint("MissingPermission")
@Composable
fun BluetoothPrinterScreen(
    modifier: Modifier = Modifier,
    bluetoothPrinterHelper: BluetoothPrinterHelper = BluetoothPrinterHelper(LocalContext.current as ComponentActivity),
    idReceiverOrder: Long,
    vmListOrder: DetailPersonOrdersViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    vmStoreData: AdminViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(
                LocalContext.current
            )
        )
    ),
) {

    var mReceiverListOrder: List<ReceiverDetailOrder>? = null
    var mDataStore: StoreData? = null

    vmListOrder.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                vmListOrder.getListOrderItems(idReceiverOrder)
            }

            is UiState.Success -> {
                mReceiverListOrder = uiState.data
            }

            is UiState.Error -> {}
        }
    }
    vmStoreData.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> vmStoreData.getStoreData()
            is UiState.Success -> {
                mDataStore = uiState.data[0]
            }

            is UiState.Error -> {}
        }
    }

    var selectedPrinter by remember { mutableStateOf<BluetoothDevice?>(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                bluetoothPrinterHelper.enableBluetooth()
            }
        }

    DisposableEffect(key1 = null) {
        if (!bluetoothPrinterHelper.hasBluetoothPermissions()) {
            launcher.launch(Manifest.permission.BLUETOOTH)
            launcher.launch(Manifest.permission.BLUETOOTH_ADMIN)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                launcher.launch(Manifest.permission.BLUETOOTH_CONNECT)
            }
        } else {
            bluetoothPrinterHelper.enableBluetooth()
        }

        onDispose {
            // Cleanup code if needed
        }
    }

    val pairedPrinters = bluetoothPrinterHelper.getPairedPrinters() ?: listOf()

    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(contentPadding = PaddingValues(8.dp), modifier = modifier) {
            items(pairedPrinters.toList()) { printer ->
                Text(text = printer.name ?: "Unknown device")
                Button(onClick = {
                    selectedPrinter = printer
                    val socket = bluetoothPrinterHelper.connectToBluetoothPrinter(printer)
                    if (socket != null) {
                        val textBuilder = BluetoothTextBuilder()
                        textBuilder.receiptDataText(
                            mDataStore = mDataStore,
                            mDataOrderList = mReceiverListOrder
                        )
                        val finalText: String = textBuilder.buildText()

                        bluetoothPrinterHelper.printText(socket, finalText)
                    }
                }) {
                    Text(text = "Select")
                }
            }
        }
    }
}
