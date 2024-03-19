package com.fatalien.scannerapp.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme

@Composable
fun TestFunctions(
    clearCatalog: () -> Unit,
    createDefaultCatalog: () -> Unit,
    scanQR: (qr: String) -> Unit
) {
    Column(Modifier.padding(10.dp)) {
        Text("Тестовые функции:")
        Spacer(Modifier.height(6.dp))
        OutlinedCard {
            Column(
                Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Button(onClick = clearCatalog) {
                    Text("Очистить каталог")
                }
                Button(onClick = createDefaultCatalog) {
                    Text("Каталог по умолчанию")
                }
                Text(text = "Сканировать штрих-код:")
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    var qrCodeNumber = 1
                    val qrCodes = List(3, { "QR${qrCodeNumber++}" })
                    qrCodes.forEach { qr ->
                        Button(onClick = { scanQR(qr) }) {
                            Text(qr)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TestFunctionPreview() {
    ScannerAppTheme {
        TestFunctions(clearCatalog = {}, createDefaultCatalog = {}, scanQR = {})
    }
}