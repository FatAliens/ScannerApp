package com.fatalien.scannerapp.screens.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.fatalien.scannerapp.data.entity.CatalogItem
import com.fatalien.scannerapp.navigation.CatalogInfoTopAppBar
import com.fatalien.scannerapp.navigation.ui.AppScaffold
import com.fatalien.scannerapp.ui.components.CatalogItemList
import com.fatalien.scannerapp.ui.components.FilePicker

@Composable
fun ProductsScreen(viewModel: ProductsScreenVM, navHostController: NavHostController) {
    val catalog by viewModel.catalog.collectAsState()
    var showCatalogDialog by remember { mutableStateOf(false) }

    AppScaffold(navHostController, {
        if (catalog.isNotEmpty()) CatalogInfoTopAppBar(catalogSize = catalog.size) {
            showCatalogDialog = true
        }
    }) {
        if (showCatalogDialog)
            CatalogDialog(catalog, { viewModel.loadCatalogFromFile(it) })
            { showCatalogDialog = false }
    }
}

@Composable
fun CatalogDialog(
    catalog: List<CatalogItem>,
    onFilePick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Card(Modifier.fillMaxSize()) {
            Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                CatalogItemList(catalog, Modifier.weight(1f))
                Row {
                    OutlinedButton(onClick = onDismissRequest) {
                        Text("Закрыть")
                    }
                    Spacer(Modifier.width(5.dp))
                    FilePicker(onFilePick)
                }
            }
        }
    }
}

@Composable
fun ClearCatalogButton(onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier.fillMaxWidth()
    ) {
        Text("ClearCatalog")
    }
}