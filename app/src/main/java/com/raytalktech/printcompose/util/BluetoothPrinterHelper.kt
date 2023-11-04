package com.raytalktech.printcompose.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.UUID

class BluetoothPrinterHelper(private val activity: Activity) {

    private val bluetoothAdapter: BluetoothAdapter? = getBluetoothAdapter(activity)

    private fun getBluetoothAdapter(activity: Activity): BluetoothAdapter? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val bluetoothManager =
                activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothManager.adapter
        } else {
            BluetoothAdapter.getDefaultAdapter()
        }
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled ?: false
    }

    @SuppressLint("MissingPermission")
    fun enableBluetooth() {
        if (hasBluetoothPermissions()) {
            if (!isBluetoothEnabled()) {
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                activity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
            }
        } else {
            requestBluetoothPermissions()
        }
    }

    fun hasBluetoothPermissions(): Boolean {
        val bluetoothPermission =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH)
        val bluetoothAdminPermission =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_ADMIN)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val bluetoothConnectPermission =
                ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
            bluetoothPermission == PackageManager.PERMISSION_GRANTED && bluetoothAdminPermission == PackageManager.PERMISSION_GRANTED && bluetoothConnectPermission == PackageManager.PERMISSION_GRANTED
        } else {
            bluetoothPermission == PackageManager.PERMISSION_GRANTED && bluetoothAdminPermission == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH_CONNECT
                ),
                BLUETOOTH_PERMISSION_REQUEST_CODE
            )
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN),
                BLUETOOTH_PERMISSION_REQUEST_CODE
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun getPairedPrinters(): Set<BluetoothDevice>? {
        return if (hasBluetoothPermissions()) {
            bluetoothAdapter?.bondedDevices
        } else {
            requestBluetoothPermissions()
            null
        }
    }

    @SuppressLint("MissingPermission")
    fun connectToBluetoothPrinter(printerDevice: BluetoothDevice): BluetoothSocket? {
        val uuid: UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Standard SerialPortService ID
        return try {
            val socket: BluetoothSocket? = printerDevice.createRfcommSocketToServiceRecord(uuid)
            socket?.connect()
            socket
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun printText(socket: BluetoothSocket, text: String) {
        try {
            val outputStream = socket.outputStream
            outputStream.write(text.toByteArray())
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Rest of the helper functions
    companion object {
        const val REQUEST_ENABLE_BLUETOOTH = 1
        const val BLUETOOTH_PERMISSION_REQUEST_CODE = 101
    }
}
