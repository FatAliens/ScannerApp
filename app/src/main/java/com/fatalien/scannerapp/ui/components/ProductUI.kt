package com.fatalien.scannerapp.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatalien.scannerapp.data.entity.ProductWithCatalog
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset

@Composable
fun ProductList(products: List<ProductWithCatalog>) {
    Card(Modifier.padding(10.dp)) {
        Column(Modifier.padding(5.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            products.forEach { product ->
                Product(product)
            }
        }
    }
}

@Composable
fun Product(product: ProductWithCatalog) {
    ListItem(
        headlineContent = {
            Text(product.title)
        },
        overlineContent = {
            Text("QR: " + product.qr)
        },
        supportingContent = {
            Text("до " + product.bestBeforeDate.toDateString())
        },
        leadingContent = {
            Card {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.sizeIn(40.dp, 80.dp, 70.dp, 140.dp)
                ) {
                    Text(product.quantity.toString())
                    Text("шт.", fontSize = 10.sp)
                }
            }
        }
    )
}

@Composable
@Preview
fun ProductListPreview() {
    ScannerAppTheme {
        val products = listOf(
            ProductWithCatalog(
                catalogId = 0, productId = 0,
                title = "Macciato", qr = "QR1", quantity = 10,
                bestBeforeDate = LocalDate.now().plusDays(22).toEpochMilli()
            ),
            ProductWithCatalog(
                catalogId = 0, productId = 0,
                title = "Cappuccino", qr = "QR2", quantity = 1,
                bestBeforeDate = LocalDate.now().plusMonths(11).toEpochMilli()
            ),
            ProductWithCatalog(
                catalogId = 0, productId = 0,
                title = "Americano", qr = "QR3", quantity = 200,
                bestBeforeDate = LocalDate.now().plusYears(5).toEpochMilli()
            )
        )
        ProductList(products)
    }
}

@Composable
@Preview
fun ProductPreview() {
    ScannerAppTheme {
        Product(
            product = ProductWithCatalog(
                1,
                1,
                "293476293",
                "Cappuconi de Macciato on Essente.",
                20,
                LocalDate.now().plusDays(950).toEpochMilli()
            )
        )
    }
}

fun LocalDate.toEpochMilli(): Long {
    return this.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
}

fun Long.toDateString(): String {
    return SimpleDateFormat("dd.MM.yyyy").format(this)
}