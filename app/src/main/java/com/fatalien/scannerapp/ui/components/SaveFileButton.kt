package com.fatalien.scannerapp.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.fatalien.scannerapp.R

@Composable
fun SaveFileButton(modifier: Modifier = Modifier, onSaveFile: (folderPath: String) -> Unit) {
    var showPicker by rememberSaveable { mutableStateOf(false) }

    OutlinedButton(onClick = { showPicker = true }, modifier) {
        Text(text = "Сохранить")
        Icon(ImageVector.vectorResource(R.drawable.file_save),"")
    }

    SaveFilePicker(
        show = showPicker,
        filename = "result",
        fileExtension = "text/comma-separated-values"
    ) { path ->
        showPicker = false;
        if (path != null) {
            onSaveFile(path.toString())
        } else {
            throw Exception("Error on save file: file is null")
        }
    }
}

@Composable
fun SaveFilePicker(
    show: Boolean,
    filename: String,
    fileExtension: String?,
    onFileSelected: (Uri?) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(fileExtension ?: "*/*"),
        onResult = onFileSelected
    )

    LaunchedEffect(show) {
        if (show) {
            launcher.launch(filename)
        }
    }
}