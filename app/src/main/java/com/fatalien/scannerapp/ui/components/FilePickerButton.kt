package com.fatalien.scannerapp.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.FilePicker

@Composable
fun FilePickerButton(
    onPickFile: (filePath: String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit = { Text("Загрузить из файла...") },
) {
    var showPicker by remember { mutableStateOf(false) }

    OutlinedButton(onClick = { showPicker = true }, modifier, content = content)

    FilePicker(show = showPicker, fileExtensions = listOf("csv")) { file ->
        showPicker = false;
        if (file != null) {
            onPickFile(file.path)
        } else {
            throw Exception("Error on pick file: file is null")
        }
    }
}