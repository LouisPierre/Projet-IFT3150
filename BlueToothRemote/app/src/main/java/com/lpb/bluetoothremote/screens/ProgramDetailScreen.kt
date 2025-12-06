package com.lpb.bluetoothremote.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lpb.bluetoothremote.Program
import com.lpb.bluetoothremote.Sound

@Composable
fun ProgramDetailScreen(
    program: Program,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(program.name, style = MaterialTheme.typography.titleLarge)

        Button(onClick = {
            // TODO: Delete program
        }) {
            Text("Delete Program")
        }

        Button(onClick = {
            val newSound = Sound(name = "Sound ${program.sounds.size + 1}", loopState = 0)
            program.sounds.add(newSound)
        }) {
            Text("Add Sound")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(program.sounds) { sound ->
                Text(
                    text = sound.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            val soundIndex = program.sounds.indexOf(sound)
                            navController.navigate("sound_editor/${program.sounds.indexOf(sound)}/${program.sounds.indexOf(sound)}")
                        }
                )
                Divider()
            }
        }
    }
}
