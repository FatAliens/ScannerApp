@file:OptIn(ExperimentalMaterial3Api::class)

package com.fatalien.scannerapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.fatalien.scannerapp.R
import com.fatalien.scannerapp.data.entity.OrderItem
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.helpers.toDateString
import com.fatalien.scannerapp.helpers.toEpochMilli
import com.fatalien.scannerapp.helpers.toLocalDate
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun UpdateOrderItemBottomSheet(
    orderItemInitData: OrderItem,
    onDismiss: () -> Unit,
    onSave: (OrderItem) -> Unit,
) {

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()


    ModalBottomSheet(onDismissRequest = { onDismiss() }, sheetState = bottomSheetState) {
        UpdateOrderItemForm(orderItemInitData)
        { newOrderItem ->
            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    onSave(newOrderItem)
                }
            }
        }
    }

}

@Composable
private fun UpdateOrderItemForm(
    orderItemInitData: OrderItem,
    onProductSubmit: (OrderItem) -> Unit
) {
    var orderItemState by remember {
        mutableStateOf(orderItemInitData)
    }
    var showDateDialog by remember {
        mutableStateOf(false)
    }

    Column(Modifier.padding(20.dp)) {
        Text(
            text = orderItemState.title,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Штрих-код: " + orderItemState.qrCode,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.height(10.dp))
        NumericUpDown(
            value = orderItemState.quantity,
            range = 0..orderItemState.requiredQuantity
        ) {
            orderItemState = orderItemState.copy(quantity = it)
        }
        Spacer(Modifier.height(10.dp))
        DatePickerButton(
            orderItemState.bestBeforeDate,
            orderItemState.requiredBestBeforeDate
        ) { showDateDialog = true }
        if (showDateDialog) {
            AppDatePickerDialog(onDismiss = { showDateDialog = false }) {
                showDateDialog = false
                orderItemState = orderItemState.copy(bestBeforeDate = it)
            }
        }
        Spacer(Modifier.height(10.dp))


        Button(
            { onProductSubmit(orderItemState) },
            Modifier
                .fillMaxWidth()
                .padding(25.dp, 10.dp),
            orderItemState.quantity > 0
        ) {
            Text("Добавить")
        }
    }
}

@Composable
private fun DatePickerButton(
    currentDate: Long,
    requiredDate: Long,
    modifier: Modifier = Modifier,
    showDialog: () -> Unit
) {
    val dateCompare = requiredDate.toLocalDate() == currentDate.toLocalDate()

    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Text("Годен до: ")
        OutlinedButton(
            onClick = showDialog,
            shape = MaterialTheme.shapes.small
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(currentDate.toDateString(), style = MaterialTheme.typography.titleMedium)
            }
        }
        Icon(
            Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
            "",
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            "Нажмите\nчтобы изменить",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppDatePickerDialog(onDismiss: () -> Unit, onComplete: (date: Long) -> Unit) {
    val datePickerState = rememberDatePickerState(
        yearRange = 2000..2100,
        initialDisplayMode = DisplayMode.Input
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = true),
        confirmButton = {
            TextButton(
                onClick = { onComplete(datePickerState.selectedDateMillis!!) },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text("Выбрать")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Отмена")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


@Composable
private fun NumericUpDown(
    modifier: Modifier = Modifier,
    value: Int,
    range: IntRange,
    onChange: (newValue: Int) -> Unit
) {
    Column(modifier.padding(5.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                { onChange(value + 1) },
                Modifier
                    .padding(5.dp, 0.dp)
                    .size(35.dp),
                enabled = value + 1 <= range.last
            ) {
                Icon(
                    ImageVector.vectorResource(R.drawable.add_circle),
                    "",
                    Modifier.fillMaxSize()
                )
            }
            Text(
                value.toString(),
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                { onChange(value - 1) },
                Modifier
                    .padding(5.dp, 0.dp)
                    .size(35.dp),
                enabled = value - 1 >= range.first
            ) {
                Icon(
                    ImageVector.vectorResource(R.drawable.remove_circle),
                    "",
                    Modifier.fillMaxSize()
                )
            }
            Text("коробок")
            Spacer(Modifier.width(5.dp))
            Text(
                "от ${range.first} до ${range.last}",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onChange(it.toInt()) },
            valueRange = range.first.toFloat()..range.last.toFloat(),
            steps = range.last / 2
        )
    }
}

@AtolPreview
@Composable
private fun NewProductFormPreview() {
    ScannerAppTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(verticalArrangement = Arrangement.Bottom) {
                UpdateOrderItemForm(
                    orderItemInitData = OrderItem(
                        "234234243",
                        "Macciato de Empresso en Ephiope",
                        20,
                        100,
                        "20.10.2003".toEpochMilli(),
                        "20.10.2023".toEpochMilli(),
                        0
                    )
                ) {}
            }

        }
    }
}

@AtolPreview
@Composable
private fun NumericUpDownPreview() {
    ScannerAppTheme {
        Surface(Modifier.fillMaxSize()) {
            NumericUpDown(
                Modifier
                    .padding(10.dp)
                    .wrapContentSize(),
                value = 50,
                onChange = {},
                range = 0..10
            )
        }
    }
}

@AtolPreview
@Composable
private fun DatePickerButtonPreview() {
    ScannerAppTheme {
        Surface(Modifier.fillMaxSize()) {
            Column {
                DatePickerButton(LocalDate.now().toEpochMilli(), "20.10.2003".toEpochMilli()) {}
                DatePickerButton(LocalDate.now().toEpochMilli(), "17.04.2024".toEpochMilli()) {}
            }
        }
    }
}