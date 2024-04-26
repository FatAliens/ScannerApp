package com.fatalien.scannerapp.navigation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.fatalien.scannerapp.R
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.ui.components.FilePickerButton
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTopAppBar(orderCompleted: Boolean, onPickFile: (String) -> Unit){
    TopAppBar(
        title = {
            if(orderCompleted)
                Text("Заказ собран", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleSmall)
            else
                Text("Заказ не собран", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.titleSmall)
        },
        actions = {
            FilePickerButton(onPickFile)
            {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Icon(ImageVector.vectorResource(R.drawable.file_open), "", tint = MaterialTheme.colorScheme.primary)
                    Text("Открыть заказ")
                }
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AtolPreview
@Composable
private fun CatalogInfoTopAppBarPreview() {
    ScannerAppTheme {
        Scaffold(topBar = { OrderTopAppBar(true) {} }) {}
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AtolPreview
@Composable
private fun EmptyCatalogTopAppBarPreview() {
    ScannerAppTheme {
        Scaffold(topBar = { OrderTopAppBar(false) {} }) {}
    }
}