package com.fatalien.scannerapp.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TestFunctions(
    scanQR: (qr: String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(Modifier.padding(0.dp).fillMaxWidth()) {
        Column(
            Modifier.padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(text = "Сканировать штрих-код:")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                var qrCodeNumber = 1
                val qrCodes = listOf("8000070112872", "4008167270515", "4006581104034", "не работает")
                qrCodes.forEach { qr ->
                    ElevatedSuggestionChip({ scanQR(qr) },
                        {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Spacer(Modifier.width(2.dp))
                                Text(qr)
                            }
                        }
                    )
                }
            }
        }
    }
}

@AtolPreview
@Composable
private fun TestFunctionPreview() {
    ScannerAppTheme {
        TestFunctions(scanQR = {})
    }
}