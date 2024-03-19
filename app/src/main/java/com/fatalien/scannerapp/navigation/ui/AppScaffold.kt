package com.fatalien.scannerapp.navigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fatalien.scannerapp.helpers.AtolPreview
import com.fatalien.scannerapp.navigation.CatalogInfoTopAppBar
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme

@Composable
fun AppScaffold(
    navHostController: NavHostController,
    topAppBar: @Composable () -> Unit,
    slot: @Composable ColumnScope.() -> Unit
) {
    ScannerAppTheme {
        Scaffold(
            bottomBar = { AppBottomNavBar(navHostController) },
            topBar = topAppBar
        ) { paddings ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddings)
                    .padding(10.dp)
            ) {
                slot()
            }
        }
    }
}

@AtolPreview
@Composable
fun AppScaffoldPreview() {
    AppScaffold(navHostController = rememberNavController(), { CatalogInfoTopAppBar(74) {} }) {
        Text("Content")
    }
}