package com.lpb.bluetoothremote

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lpb.bluetoothremote.screens.*
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
            val programs = remember { mutableStateListOf<Program>() }
            val plugins = remember { mutableStateListOf<String>() }
            var volume by remember { mutableStateOf(0.5f) }
            var status by remember { mutableStateOf("Connecting...") }
            var connected by remember { mutableStateOf(false) }

            val navController = rememberNavController()

            // --- Bluetooth connection ---
            LaunchedEffect(Unit) {
                connectToServer(
                    onConnected = {
                        connected = true
                        sendToServer("connect")
                        status = "Connected"
                    },
                    onMessageReceived = { msg ->
                        val lines = msg.split("\n")
                        for (line in lines) {
                            if (line.isNotBlank() && line != "END_OF_PLUGINS") {
                                plugins.add(line)
                            }
                        }
                    },
                    onDisconnected = {
                        connected = false
                        status = "Disconnected"
                    }
                )
            }

            NavHost(
                navController = navController,
                startDestination = "programs"
            ) {
                // --- ProgramsScreen (home) ---
                composable("programs") {
                    ProgramsScreen(
                        programs = programs,
                        navController = navController,
                        onGetPlugins = {
                            plugins.clear()
                            sendToServer("get_plugins")
                        },
                        onCreateProgram = { programName ->
                            sendToServer("create_program:$programName")
                        }
                    )
                }

                // --- ProgramDetailScreen ---
                composable("program_detail/{programIndex}") { backStackEntry ->
                    val index = backStackEntry.arguments?.getString("programIndex")?.toInt() ?: 0
                    ProgramDetailScreen(
                        program = programs[index],
                        navController = navController
                    )
                }

                // --- PluginsScreen ---
                composable("plugins") {
                    PluginsScreen(
                        plugins = plugins,
                        onBack = { navController.popBackStack() }
                    )
                }

                // --- ControlScreen ---
                composable("control") {
                    ControlScreen(
                        connected = connected,
                        volume = mutableStateOf(volume),
                        onVolumeChange = { newVolume ->
                            volume = newVolume
                            sendToServer("volume:$newVolume")
                        },
                        onRunScript = { sendToServer("run_reascript") },
                        navController = navController
                    )
                }
            }
        }
    }

    // --- Bluetooth functions ---
    private fun connectToServer(
        onConnected: () -> Unit,
        onMessageReceived: (String) -> Unit,
        onDisconnected: () -> Unit
    ) {
        ioScope.launch {
            try {
                val device: BluetoothDevice =
                    bluetoothAdapter!!.getRemoteDevice(serverMac)

                val m = device.javaClass.getMethod(
                    "createRfcommSocket",
                    Int::class.javaPrimitiveType
                )
                btSocket = m.invoke(device, 1) as BluetoothSocket

                bluetoothAdapter.cancelDiscovery()
                btSocket!!.connect()
                withContext(Dispatchers.Main) { onConnected() }

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
