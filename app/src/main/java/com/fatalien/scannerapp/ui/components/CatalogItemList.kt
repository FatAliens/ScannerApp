package com.fatalien.scannerapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatalien.scannerapp.data.entity.CatalogItem
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme

@Composable
fun CatalogItemList(catalog: List<CatalogItem>, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text("Каталог товара:")
        Spacer(Modifier.height(6.dp))
        OutlinedCard{
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(catalog) { item ->
                    CatalogItem(item)
                }
            }
        }
    }
}
@Composable
fun CatalogItem(catalogItem: CatalogItem) {
    Card(Modifier.padding(5.dp)) {
        Column(Modifier.padding(5.dp)) {
            Text(catalogItem.title, style = MaterialTheme.typography.titleLarge)
            Row {
                Text("Штрих-код: ", color = MaterialTheme.colorScheme.secondary)
                Text(catalogItem.qrCode, color = MaterialTheme.colorScheme.tertiary)
            }
        }
    }
}

@Composable
@AtolPreview
fun CatalogItemPreview() {
    ScannerAppTheme {
        CatalogItem(CatalogItem(1, "2394239", "Macciato de Lanposso en-Erichto."))
    }
}

@Composable
@AtolPreview
fun CatalogPreview() {
    ScannerAppTheme {
        CatalogItemList(
            catalog = listOf(
                CatalogItem(
                    qrCode = "4008167135500",
                    title = "Lavazza Espresso 250GR Tin włoska"
                ),
                CatalogItem(
                    qrCode = "4008167154693",
                    title = "Lavazza NCC Coffee caps Espresso Decaffeinato 58gr"
                ),
                CatalogItem(
                    qrCode = "4008167152781",
                    title = "IDEE KAFFEE Espresso Inspirierend Intensiv 750gr"
                )
            )
        )
    }
}