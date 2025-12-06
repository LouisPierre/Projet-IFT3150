package com.lpb.bluetoothremote.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lpb.bluetoothremote.Program

@Composable
fun ProgramsScreen(
    programs: MutableList<Program>,
    navController: NavController,
    onGetPlugins: (() -> Unit)? = null,
    onCreateProgram: ((String) -> Unit)? = null
) {
    var showDialog by remember { mutableStateOf(false) }
    var newProgramName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Programs", style = MaterialTheme.typography.titleLarge)

        Button(onClick = { showDialog = true }) {
            Text("Add New Program")
        }

        Button(onClick = { navController.navigate("control") }) {
            Text("Control Reaper")
        }

        Button(
            onClick = {
                onGetPlugins?.invoke()
                navController.navigate("plugins")
            }
        ) {
            Text("View Plugins")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(programs) { program ->
                Text(
                    text = program.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val index = programs.indexOf(program)
                            navController.navigate("program_detail/$index")
                        }
                        .padding(8.dp)
                )
                Divider()
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("New Program") },
            text = {
                OutlinedTextField(
                    value = newProgramName,
                    onValueChange = { newProgramName = it },
                    label = { Text("Program Name") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (newProgramName.isNotBlank()) {
                        programs.add(Program(name = newProgramName))
                        onCreateProgram?.invoke(newProgramName)
                        newProgramName = ""
                        showDialog = false
                    }
                }) {
                    Text("Create")
                }
            },
            dismissButton = {
                Button(onClick = {
                    newProgramName = ""
                    showDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}
