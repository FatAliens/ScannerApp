package com.fatalien.scannerapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import com.fatalien.scannerapp.data.entity.CatalogItem
import com.fatalien.scannerapp.data.entity.Product
import com.fatalien.scannerapp.ui.compose.toDateString

data class NewProductState(
    val showDialog: Boolean = false,
    val product: Product? = null,
    val catalogItem: CatalogItem? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProductDialog(
    state: NewProductState,
    onChangeProductQuantity: (Int) -> Unit,
    onChangeProductBBD: (Long) -> Unit,
    onComplete: ()->Unit,
    onDismiss: () -> Unit
) {

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val datePickerState = rememberDatePickerState(
        yearRange = 2000..2100,
        initialDisplayMode = DisplayMode.Input
    )
    var showDateDialog by remember {
        mutableStateOf(false)
    }

    ModalBottomSheet(onDismissRequest = { onDismiss() }, sheetState = bottomSheetState) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = state.catalogItem!!.title,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Штрих-код: " + state.catalogItem!!.qrCode,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = state.product!!.quantity.toString().trimStart('0'),
                onValueChange = { input ->
                    val cleanInput = input.filter { letter -> letter.isDigit() }
                    val number = if (cleanInput.isNotEmpty()) cleanInput.toInt() else 0
                    onChangeProductQuantity(number)
                },
                label = { Text("Количество коробок") },
                placeholder = { Text("Введите число") },
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.None,
                    false,
                    KeyboardType.Number,
                    ImeAction.Done
                )
            )
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Годен до: ")
                OutlinedButton(
                    onClick = { showDateDialog = true },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(state.product!!.bestBeforeDateConverted.toDateString())
                }
                Spacer(Modifier.width(10.dp))
                Icon(
                    Icons.Outlined.KeyboardArrowLeft,
                    "",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    "Нажмите,\nчтобы изменить",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Button(
                {
                    onComplete()
                },
                Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 10.dp)
            ) {
                Text("Добавить")
            }
        }
    }
    if (showDateDialog) {
        DatePickerDialog(
            onDismissRequest = { showDateDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDateDialog = false
                        onChangeProductBBD(datePickerState.selectedDateMillis!!)
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDateDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
