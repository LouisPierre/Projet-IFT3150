package com.lpb.bluetoothremote

import androidx.compose.runtime.mutableStateListOf

data class Program(
    var name: String,
    val sounds: MutableList<Sound> = mutableStateListOf()
)