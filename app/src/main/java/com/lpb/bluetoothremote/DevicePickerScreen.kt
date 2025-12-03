package com.lpb.bluetoothremote

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun DevicePickerScreen(onDeviceSelected: (BluetoothDevice) -> Unit) {
    val context = LocalContext.current

    // Check Bluetooth permission
    val hasPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                    Manifest.permission.BLUETOOTH_CONNECT
                else
                    Manifest.permission.BLUETOOTH
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    if (!hasPermission.value) {
        Text("Bluetooth permission required", style = MaterialTheme.typography.titleLarge)
        return
    }

    val adapter = BluetoothAdapter.getDefaultAdapter()
    val bondedDevices = adapter?.bondedDevices?.toList() ?: emptyList()

    Column(Modifier.padding(16.dp)) {
        Text("Select Bluetooth Device", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        bondedDevices.forEach { device ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onDeviceSelected(device) }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(device.name ?: "Unknown")
                    Text(device.address, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
