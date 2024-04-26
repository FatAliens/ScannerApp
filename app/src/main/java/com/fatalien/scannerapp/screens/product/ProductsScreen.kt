package com.fatalien.scannerapp.screens.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fatalien.scannerapp.R
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.navigation.ui.AppScaffold
import com.fatalien.scannerapp.navigation.ui.CatalogInfoTopAppBar
import com.fatalien.scannerapp.navigation.ui.SaveProductsFAB
import com.fatalien.scannerapp.ui.components.CatalogDialog
import com.fatalien.scannerapp.ui.components.NewProductBottomSheet
import com.fatalien.scannerapp.ui.compose.ProductList
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme

@Composable
fun ProductsScreen(viewModel: ProductsScreenVM, navHostController: NavHostController) {
    val catalog by viewModel.catalog.collectAsState()
    val scannedProduct by viewModel.scannedProduct.collectAsState()
    val products by viewModel.products.collectAsState()
    var showCatalogDialog by remember { mutableStateOf(false) }

    AppScaffold(
        navHostController,
        { CatalogInfoTopAppBar(catalog.size) { showCatalogDialog = true } },
        { if(products.isNotEmpty()) SaveProductsFAB(viewModel::saveProductsToFile) }
    ) {
        if (showCatalogDialog) CatalogDialog(catalog,
            { viewModel.loadCatalogFromFile(it) }) { showCatalogDialog = false }
        if (scannedProduct != null) {
            NewProductBottomSheet(
                productInitData = scannedProduct!!, onDismiss = viewModel::dismissNewProduct
            ) {
                viewModel.dismissNewProduct()
                if(it.id>0){
                    viewModel.updateProduct(it)
                }
                else {
                    viewModel.insertProduct(it)
                }
            }
        }
        if (products.isNotEmpty()) {
            //TestFunctions(viewModel::emulateQrScan)
            ProductList(products, viewModel::selectProduct, viewModel::deleteProductById)
        } else {
            //TestFunctions(viewModel::emulateQrScan, Modifier.weight(1f))
            EmptyProductsTooltip()
        }

    }
}

@Composable
fun EmptyProductsTooltip(){
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(ImageVector.vectorResource(R.drawable.barcode_scanner),"", Modifier.size(100.dp))
        Spacer(Modifier.height(10.dp))
        Text("Осканируйте товар,\nчтобы увидеть его здесь.", textAlign = TextAlign.Center)
    }
}

@AtolPreview
@Composable
fun EmptyProductsTooltipPreview(){
    ScannerAppTheme {
        Surface {
            EmptyProductsTooltip();
        }
    }
}