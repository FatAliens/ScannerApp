package com.fatalien.scannerapp.screens.order

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fatalien.scannerapp.R
import com.fatalien.scannerapp.data.entity.OrderItem
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.helpers.toDateString
import com.fatalien.scannerapp.helpers.toEpochMilli
import com.fatalien.scannerapp.helpers.toLocalDate
import com.fatalien.scannerapp.navigation.ui.AppScaffold
import com.fatalien.scannerapp.ui.components.FilePickerButton
import com.fatalien.scannerapp.ui.components.NewOrderItemBottomSheet
import com.fatalien.scannerapp.ui.components.SaveFileButton
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme
import java.time.LocalDate

@Composable
fun OrderScreen(viewModel: OrderScreenVM, navHostController: NavHostController) {
    val orderItems by viewModel.orderItems.collectAsState()
    val scannedItem by viewModel.scannedOrderItem.collectAsState()

    AppScaffold(navHostController, {}, {}) {

        if (orderItems.size > 0) {
            LazyColumn(Modifier.weight(1f)) {
                //item {
                //    TestFunctions(viewModel::emulateQrScan, Modifier)
                //}
                items(orderItems, key = { t -> t.id }) { item ->
                    OrderListItem(item)
                }
            }
            Spacer(Modifier.height(5.dp))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                FilePickerButton(viewModel::readOrderFromFile, "Загрузить другой заказ..")
            }
            Row(Modifier.fillMaxWidth()) {
                SaveFileButton(Modifier.weight(1f), viewModel::saveOrderToFile)
                Spacer(Modifier.width(4.dp))
                Button(
                    onClick = viewModel::clearOrder,
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.onErrorContainer)
                ) {
                    Text("Удалить заказ")
                }
            }
        } else {
            EmptyOrderDialog(viewModel::readOrderFromFile)
        }
        if (scannedItem != null) {
            NewOrderItemBottomSheet(scannedItem!!, onDismiss = viewModel::dismissNewItemDialog) {
                viewModel.dismissNewItemDialog()
                viewModel.updateOrderItem(it)
            }
        }
    }
}

@Composable
private fun EmptyOrderDialog(
    onFilePick: (String) -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            "Заказ не выбран",
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
    }
}

@AtolPreview
@Composable
private fun EmptyOrderDialogPreview() {
    ScannerAppTheme {
        Surface {
            EmptyOrderDialog {

            }
        }
    }
}

@Composable
private fun OrderListItem(item: OrderItem) {
    val bestBeforeDateCompare =
        item.bestBeforeDate.toLocalDate() == item.requiredBestBeforeDate.toLocalDate()
    val quantityCompare = item.quantity == item.requiredQuantity
    val orderItemCompare = bestBeforeDateCompare && quantityCompare

    ListItem(
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceDim),
        headlineContent = {
            Text(item.title)
        },
        overlineContent = {
            Text("QR: " + item.qrCode)
        },
        supportingContent = {
            if (item.quantity == 0) {
                Text(
                    "до " + item.requiredBestBeforeDate.toDateString(),
                    color = MaterialTheme.colorScheme.error
                )
            } else if (bestBeforeDateCompare) {
                Text("до " + item.requiredBestBeforeDate.toDateString())
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("до " + item.requiredBestBeforeDate.toDateString())
                    Text(
                        item.requiredBestBeforeDate.toDateString(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough)
                    )
                }
            }
        },
        leadingContent = {
            Surface(
                Modifier.sizeIn(50.dp, 50.dp, 70.dp, 50.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AnimatedContent(item.quantity, Modifier.weight(1f), {
                        scaleIn(animationSpec = tween(durationMillis = 150)) togetherWith
                                scaleOut(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioHighBouncy,
                                        stiffness = Spring.StiffnessHigh
                                    )
                                )
                    }, label = "Animate Product Count") {
                        val textColor =
                            if (quantityCompare)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else MaterialTheme.colorScheme.error
                        Text(
                            it.toString(),
                            textAlign = TextAlign.Center,
                            color = textColor
                        )
                    }
                    VerticalDivider(thickness = 2.dp)
                    Text(
                        item.requiredQuantity.toString(),
                        Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        trailingContent = {
            if (orderItemCompare) {
                Icon(
                    ImageVector.vectorResource(R.drawable.chech),
                    "",
                    Modifier.size(50.dp),
                    MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                )
            } else {
                Icon(
                    ImageVector.vectorResource(R.drawable.warning),
                    "",
                    Modifier.size(50.dp),
                    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.70f),
                )
            }

        }
    )
}

@AtolPreview
@Composable
private fun OrderListItemPreview() {
    ScannerAppTheme {
        Column {
            OrderListItem(
                OrderItem(
                    "2352332424",
                    "Cappucino",
                    0,
                    10,
                    0,
                    LocalDate.now().toEpochMilli()
                )
            )
            OrderListItem(
                OrderItem(
                    "2352332424",
                    "Cappucino",
                    5,
                    10,
                    LocalDate.now().plusDays(-20).toEpochMilli(),
                    LocalDate.now().toEpochMilli()
                )
            )
            OrderListItem(
                OrderItem(
                    "2352332424",
                    "Cappucino",
                    10,
                    10,
                    LocalDate.now().toEpochMilli(),
                    LocalDate.now().toEpochMilli()
                )
            )
        }
    }
}