package com.lpb.bluetoothremote.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ControlScreen(
    connected: Boolean,
    volume: MutableState<Float>,
    onVolumeChange: (Float) -> Unit,
    onRunScript: () -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }

        Button(onClick = onRunScript, enabled = connected) {
            Text("Run Reaper Script")
        }

        Column {
            Text("Volume: ${(volume.value * 100).toInt()}%")
            Slider(
                value = volume.value,
                onValueChange = {
                    volume.value = it
                    onVolumeChange(it)
                },
                valueRange = 0f..1f,
                enabled = connected
            )
        }
    }
}
