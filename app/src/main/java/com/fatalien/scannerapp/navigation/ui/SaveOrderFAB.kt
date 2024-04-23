package com.fatalien.scannerapp.navigation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.fatalien.scannerapp.R
import com.fatalien.scannerapp.ui.components.SaveFilePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveOrderFAB(onFileSave: (filePath: String) -> Unit) {
    var showPicker by rememberSaveable { mutableStateOf(false) }

    SaveFilePicker(
        show = showPicker,
        filename = "result",
        fileExtension = "text/comma-separated-values"
    ) { path ->
        showPicker = false;
        if (path != null) {
            onFileSave(path.toString())
        } else {
            //throw Exception("Error on save file: file is null")
        }
    }

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip {
                Text("Сохранить\nв файл")
            }
        },
        state = rememberTooltipState()
    ) {
        FloatingActionButton({ showPicker = true }) {
            Icon(
                ImageVector.vectorResource(R.drawable.file_save),
                "сохранить заказ в файл"
            )
        }
    }
}