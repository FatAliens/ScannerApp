package com.fatalien.scannerapp.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.darkrockstudios.libraries.mpfilepicker.FilePicker

@Composable
fun FilePicker(onPickFile: (String) -> Unit) {
    var showFilePicker by remember { mutableStateOf(false) }

    Button(onClick = { showFilePicker = true }) {
        Text(text = "Загрузить из файла...")
    }

    FilePicker(show = showFilePicker, fileExtensions = listOf("csv")) { file ->
        showFilePicker = false;
        if (file != null) {
            onPickFile(file.path)
        } else {
            throw Exception("Error on pick file: file is null")
        }
    }
}