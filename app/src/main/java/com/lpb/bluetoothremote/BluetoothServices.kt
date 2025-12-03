package com.lpb.bluetoothremote

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import kotlin.concurrent.thread

object BluetoothServices {

    private val TAG = "BluetoothServices"
    private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null
    private var inputStream: InputStream? = null

    private var listenerThread: Thread? = null

    private var onMessageReceived: ((String) -> Unit)? = null
    private var onConnected: (() -> Unit)? = null
    private var onDisconnected: (() -> Unit)? = null

    @RequiresPermission(allOf = [android.Manifest.permission.BLUETOOTH_CONNECT])
    fun connectToDevice(
        context: Context,
        device: BluetoothDevice,
        onConnected: () -> Unit,
        onDisconnected: () -> Unit,
        onMessageReceived: (String) -> Unit
    ) {
        disconnect()

        this.onConnected = onConnected
        this.onDisconnected = onDisconnected
        this.onMessageReceived = onMessageReceived

        thread {
            try {
                bluetoothSocket = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    device.createRfcommSocketToServiceRecord(SPP_UUID)
                } else {
                    device.createRfcommSocketToServiceRecord(SPP_UUID)
                }

                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                bluetoothSocket?.connect()

                outputStream = bluetoothSocket?.outputStream
                inputStream = bluetoothSocket?.inputStream

                onConnected()

                listenForMessages()
            } catch (e: IOException) {
                Log.e(TAG, "Connection failed: ${e.message}")
                onDisconnected()
            }
        }
    }

    private fun listenForMessages() {
        listenerThread = thread {
            val buffer = ByteArray(1024)
            var bytes: Int

            try {
                while (true) {
                    bytes = inputStream?.read(buffer) ?: break
                    if (bytes > 0) {
                        val msg = String(buffer, 0, bytes)
                        onMessageReceived?.invoke(msg)
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, "Error reading from Bluetooth socket: ${e.message}")
            } finally {
                onDisconnected?.invoke()
                disconnect()
            }
        }
    }

    fun sendMessage(message: String) {
        try {
            outputStream?.write((message + "\n").toByteArray(Charsets.UTF_8))
        } catch (e: IOException) {
            Log.e(TAG, "Error sending message: ${e.message}")
        }
    }

    fun disconnect() {
        try {
            listenerThread?.interrupt()
            inputStream?.close()
            outputStream?.close()
            bluetoothSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing socket: ${e.message}")
        }
        bluetoothSocket = null
        inputStream = null
        outputStream = null
        listenerThread = null
        onMessageReceived = null
        onConnected = null
        onDisconnected = null
    }
}
