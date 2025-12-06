package com.lpb.bluetoothremote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ControlScreen(
    send: (String) -> Unit,
    plugins: List<String>,
    onGetPlugins: () -> Unit,
    showPlugins: Boolean,
    setShowPlugins: (Boolean) -> Unit
) {

    var volume by remember { mutableStateOf(0.5f) }

    if (!showPlugins) {
        Column(Modifier.padding(16.dp)) {

            Button(onClick = { send("connect") }) {
                Text("Connect")
            }

            Spacer(Modifier.height(12.dp))

            Button(onClick = { send("run_reascript") }) {
                Text("Run Reaper Script")
            }

            Spacer(Modifier.height(12.dp))

            Text("Volume: ${(volume * 100).toInt()}%")
            Slider(volume, {
                volume = it
                send("volume:$it")
            })

            Spacer(Modifier.height(24.dp))

            Button(onClick = {
                send("get_plugins")
                onGetPlugins()
                setShowPlugins(true)
            }) {
                Text("Show Plugins")
            }
        }
    } else {
        Column {
            Button(onClick = { setShowPlugins(false) }, Modifier.padding(8.dp)) {
                Text("Back")
            }

            LazyColumn {
                items(plugins) { plugin ->
                    Text(plugin, Modifier.padding(8.dp))
                    Divider()
                }
            }
        }
    }
}
