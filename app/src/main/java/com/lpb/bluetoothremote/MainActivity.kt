package com.lpb.bluetoothremote

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import java.io.IOException

class MainActivity : ComponentActivity() {

    private val serverMac = "AC:5A:FC:3C:6F:EE"
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var btSocket: BluetoothSocket? = null
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionRequest.launch(
            arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )
        )

        setContent {
            var status by remember { mutableStateOf("Connecting...") }
            var connected by remember { mutableStateOf(false) }
            var volume by remember { mutableStateOf(0.5f) }
            var serverMessages by remember { mutableStateOf("") }
            var plugins by remember { mutableStateOf(listOf<String>()) }

            LaunchedEffect(Unit) {
                connectToServer(
                    onConnected = {
                        connected = true
                        status = "Connected"
                        sendToServer("connect")
                    },
                    onMessageReceived = { msg ->
                        // If server message contains plugin list, split it
                        if (msg.contains("\n")) {
                            plugins = msg.trim().split("\n")
                        } else {
                            serverMessages += msg.trim() + "\n"
                        }
                    },
                    onDisconnected = {
                        connected = false
                        status = "Disconnected"
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Server Status: $status")

                Button(
                    onClick = { sendToServer("run_reascript") },
                    enabled = connected
                ) {
                    Text("Run Reaper Script")
                }

                Column {
                    Text("Volume: ${(volume * 100).toInt()}%")
                    Slider(
                        value = volume,
                        onValueChange = {
                            volume = it
                            sendToServer("volume:$it")
                        },
                        valueRange = 0f..1f,
                        enabled = connected
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { sendToServer("get_plugins") },
                    enabled = connected
                ) {
                    Text("Get Plugin List")
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    items(plugins) { plugin ->
                        Text(
                            text = plugin,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable { /* optionally trigger plugin */ }
                        )
                        Divider()
                    }
                }
            }
        }
    }

    private fun connectToServer(
        onConnected: () -> Unit,
        onMessageReceived: (String) -> Unit,
        onDisconnected: () -> Unit
    ) {
        ioScope.launch {
            try {
                val device: BluetoothDevice = bluetoothAdapter!!.getRemoteDevice(serverMac)
                val m = device.javaClass.getMethod("createRfcommSocket", Int::class.javaPrimitiveType)
                btSocket = m.invoke(device, 1) as BluetoothSocket

                bluetoothAdapter.cancelDiscovery()
                btSocket!!.connect()
                onConnected()

                val buffer = ByteArray(1024)
                try {
                    while (true) {
                        val bytesRead = btSocket!!.inputStream.read(buffer)
                        if (bytesRead > 0) {
                            val msg = String(buffer, 0, bytesRead)
                            withContext(Dispatchers.Main) { onMessageReceived(msg) }
                        }
                    }
                } catch (e: IOException) {
                    withContext(Dispatchers.Main) { onDisconnected() }
                }

            } catch (e: IOException) {
                withContext(Dispatchers.Main) { onDisconnected() }
            }
        }
    }

    private fun sendToServer(msg: String) {
        ioScope.launch {
            try {
                btSocket?.outputStream?.write("$msg\n".toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.cancel()
        try {
            btSocket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
