package com.fatalien.scannerapp.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.CaretProperties
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.fatalien.scannerapp.R
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun CatalogInfoTopAppBar(catalogSize: Int, onCatalogDialogOpen: () -> Unit) {
    val titleText = if (catalogSize > 0) "В каталоге $catalogSize товаров" else "Каталог товара пуст"
    TopAppBar(title = { Text(titleText) }, actions = {

        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(10.dp),
            tooltip = {
                PlainTooltip{
                    Text("Открыть каталог")
                }
            },
            state = rememberTooltipState()
        ) {
            IconButton(onClick = onCatalogDialogOpen) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.preview),
                    contentDescription = "Show catalog"
                )
            }
        }
    })
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AtolPreview
@Composable
fun CatalogInfoTopAppBarPreview() {
    ScannerAppTheme {
        Scaffold(topBar = { CatalogInfoTopAppBar(85) {} }) {}
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AtolPreview
@Composable
fun EmptyCatalogTopAppBarPreview() {
    ScannerAppTheme {
        Scaffold(topBar = { CatalogInfoTopAppBar(0) {} }) {}
    }
}