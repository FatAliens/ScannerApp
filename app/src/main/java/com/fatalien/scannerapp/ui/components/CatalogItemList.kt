package com.fatalien.scannerapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fatalien.scannerapp.R
import com.fatalien.scannerapp.data.entity.CatalogItem
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme

@Composable
fun CatalogDialog(
    catalog: List<CatalogItem>,
    onFilePick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        OutlinedCard(Modifier.fillMaxSize()) {
            if (catalog.isEmpty())
                EmptyCatalogDialogContent(onDismissRequest, onFilePick)
            else
                CatalogDialogContent(catalog, onDismissRequest, onFilePick)
        }
    }
}

@Composable
fun CatalogDialogContent(
    catalog: List<CatalogItem>,
    onDismissRequest: () -> Unit,
    onFilePick: (String) -> Unit
) {
    Column(Modifier.padding(10.dp)) {
        CatalogItemList(catalog, Modifier.weight(1f))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = onDismissRequest) {
                Text("Закрыть")
            }
            Spacer(Modifier.width(5.dp))
            FilePickerButton(onFilePick)
        }
    }
}

@Composable
private fun EmptyCatalogDialogContent(
    onDismissRequest: () -> Unit,
    onFilePick: (String) -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp), contentAlignment = Alignment.Center) {
        Text(
            "Каталог пуст",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                ImageVector.vectorResource(R.drawable.file_open),
                "open file",
                modifier = Modifier.size(150.dp)
            )
            FilePickerButton(
                onFilePick,
            )
        }
        OutlinedButton(
            onClick = onDismissRequest,
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Text("Закрыть")
        }
    }
}

@Composable
private fun CatalogItemList(catalog: List<CatalogItem>, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text("Каталог товара:")
        Spacer(Modifier.height(6.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)) {
            LazyColumn(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(catalog) { item ->
                    CatalogItem(item)
                }
            }
        }
    }
}

@Composable
private fun CatalogItem(catalogItem: CatalogItem) {
    ListItem(headlineContent = { Text(catalogItem.title) },
        supportingContent = { Text("Штрих-код: ${catalogItem.qrCode}") })
}

@Composable
@AtolPreview
private fun CatalogItemPreview() {
    ScannerAppTheme {
        CatalogItem(CatalogItem(1, "2394239", "Macciato de Lanposso en-Erichto."))
    }
}

@Composable
@AtolPreview
private fun CatalogPreview() {
    ScannerAppTheme {
        CatalogItemList(
            catalog = listOf(
                CatalogItem(
                    qrCode = "4008167135500",
                    title = "Lavazza Espresso 250GR Tin włoska",
                    id = 0
                ),
                CatalogItem(
                    qrCode = "4008167154693",
                    title = "Lavazza NCC Coffee caps Espresso Decaffeinato 58gr",
                    id = 1
                ),
                CatalogItem(
                    qrCode = "4008167152781",
                    title = "IDEE KAFFEE Espresso Inspirierend Intensiv 750gr",
                    id = 2
                )
            )
        )
    }
}

@AtolPreview
@Composable
private fun CatalogDialogPreview() {
    ScannerAppTheme {
        CatalogDialog(
            catalog = listOf(
                CatalogItem(
                    qrCode = "4008167135500",
                    title = "Lavazza Espresso 250GR Tin włoska",
                    id = 0
                ),
                CatalogItem(
                    qrCode = "4008167154693",
                    title = "Lavazza NCC Coffee caps Espresso Decaffeinato 58gr",
                    id = 1
                ),
                CatalogItem(
                    qrCode = "4008167152781",
                    title = "IDEE KAFFEE Espresso Inspirierend Intensiv 750gr",
                    id = 2
                )
            ),
            onFilePick = {},
            onDismissRequest = {}
        )
    }
}

@AtolPreview
@Composable
private fun EmptyCatalogDialogPreview() {
    ScannerAppTheme {
        CatalogDialog(
            catalog = emptyList(),
            onFilePick = {},
            onDismissRequest = {}
        )
    }
}