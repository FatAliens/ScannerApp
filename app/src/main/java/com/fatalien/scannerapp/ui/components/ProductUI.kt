package com.fatalien.scannerapp.ui.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.fatalien.scannerapp.R
import com.fatalien.scannerapp.data.entity.Product
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.helpers.toDateString
import com.fatalien.scannerapp.helpers.toEpochMilli
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme
import java.time.LocalDate

@Composable
fun ProductList(products: List<Product>, onSelectItem: (id: Product) -> Unit, onDeleteItem: (id: Int) -> Unit) {
    LazyColumn(Modifier.padding(5.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        items(products, key = { t -> t.id }) { product ->
            ProductItem(product, {onSelectItem(product)}) { onDeleteItem(product.id) }
        }
    }
}

@Composable
private fun ProductItem(product: Product, onSelect: () -> Unit,onDeleteItem: () -> Unit,) {
    ListItem(
        modifier = Modifier.clickable(onClick = onSelect),
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceDim),
        headlineContent = {
            Text(product.title)
        },
        overlineContent = {
            Text("QR: " + product.qrCode)
        },
        supportingContent = {
            Text("до " + product.bestBeforeDate.toDateString())
        },
        leadingContent = {
            Surface(
                Modifier.sizeIn(50.dp, 50.dp, 70.dp, 70.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    AnimatedContent(product.quantity, transitionSpec = {
                        scaleIn(animationSpec = tween(durationMillis = 150)) togetherWith
                                scaleOut(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioHighBouncy,
                                        stiffness = Spring.StiffnessHigh
                                    )
                                )
                    }, label = "Animate Product Count") {
                        Text(it.toString())
                    }
                }
            }
        },
        trailingContent = {
            IconButton(onDeleteItem) {
                Icon(
                    ImageVector.vectorResource(R.drawable.delete),
                    "",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@Composable
@AtolPreview
private fun ProductListPreview() {
    ScannerAppTheme {
        val products = listOf(
            Product(
                title = "Lavazza Espresso 250GR Tin włoska",
                qrCode = "8000070112872",
                quantity = 10,
                id = 0,
                bestBeforeDate = LocalDate.now().plusDays(22).toEpochMilli()
            ),
            Product(
                title = "Lavazza NCC Coffee caps Espresso Decaffeinato 58gr",
                qrCode = "8000070053595",
                quantity = 1,
                id = 1,
                bestBeforeDate = LocalDate.now().plusMonths(11).toEpochMilli()
            ),
            Product(
                title = "Café Intención ecológico Cafe Crema 100% arabica",
                qrCode = "4006581801032",
                quantity = 200,
                id = 2,
                bestBeforeDate = LocalDate.now().plusYears(5).toEpochMilli()
            )
        )
        ProductList(products, {}) {}
    }
}

@Composable
@AtolPreview
private fun ProductPreview() {
    ScannerAppTheme {
        ProductItem(
            product = Product(
                "293476293",
                "Cappuconi de Macciato on Essente.",
                20,
                LocalDate.now().plusDays(950).toEpochMilli()
            )
        , {}) {}
    }
}