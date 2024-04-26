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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.fatalien.scannerapp.R
import com.fatalien.scannerapp.data.entity.Product
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.helpers.toDateString
import com.fatalien.scannerapp.helpers.toEpochMilli
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun NewProductBottomSheet(
    productInitData: Product,
    onDismiss: () -> Unit,
    onSaveProduct: (Product) -> Unit,
) {

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()


    ModalBottomSheet(onDismissRequest = { onDismiss() }, sheetState = bottomSheetState) {
        NewProductForm(productInitData)
        { newProduct ->
            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    onSaveProduct(newProduct)
                }
            }
        }
    }

}

@Composable
private fun NewProductForm(
    productInitData: Product,
    onProductSubmit: (Product) -> Unit
) {
    var productState by remember {
        mutableStateOf(productInitData)
    }
    var showDateDialog by remember {
        mutableStateOf(false)
    }

    Column(Modifier.padding(20.dp)) {
        Text(
            text = productState.title,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Штрих-код: " + productState.qrCode,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.height(10.dp))
        NumericUpDown(
            value = productState.quantity,
            range = 0..100
        ) {
            productState = productState.copy(quantity = it)
        }
        Spacer(Modifier.height(10.dp))
        DatePickerButton(productState.bestBeforeDate) { showDateDialog = true }
        if (showDateDialog) {
            AppDatePickerDialog(onDismiss = { showDateDialog = false }) {
                showDateDialog = false
                productState = productState.copy(bestBeforeDate = it)
            }
        }
        Spacer(Modifier.height(10.dp))


        Button(
            { onProductSubmit(productState) },
            Modifier
                .fillMaxWidth()
                .padding(25.dp, 10.dp),
            productState.quantity > 0
        ) {
            Text("Добавить")
        }
    }
}

@Composable
private fun DatePickerButton(
    currentDate: Long,
    modifier: Modifier = Modifier,
    showDialog: () -> Unit
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Text("Годен до: ")
        OutlinedButton(
            onClick = showDialog,
            shape = MaterialTheme.shapes.small
        ) {
            Text(currentDate.toDateString(), style = MaterialTheme.typography.titleMedium)
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
            value = value / 100f,
            onValueChange = { onChange((it * 100).toInt()) },
            steps = 19
        )
    }
}

@AtolPreview
@Composable
private fun NewProductFormPreview() {
    ScannerAppTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(verticalArrangement = Arrangement.Bottom){
                NewProductForm(
                    productInitData = Product(
                        "234234243",
                        title = "Macciato de Empresso en Ephiope",
                        10,
                        LocalDate.now().toEpochMilli(),
                        -1
                    )
                ){}
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
                range = 0..100
            )
        }
    }
}

@AtolPreview
@Composable
private fun DatePickerButtonPreview() {
    ScannerAppTheme {
        Surface(Modifier.fillMaxSize()) {
            DatePickerButton(LocalDate.now().toEpochMilli()) {}
        }
    }
}