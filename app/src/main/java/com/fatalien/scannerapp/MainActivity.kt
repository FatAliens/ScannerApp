package com.fatalien.scannerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fatalien.scannerapp.navigation.AppNavGraph
import com.fatalien.scannerapp.ui.theme.ScannerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge();
        super.onCreate(savedInstanceState)
        setContent {
            ScannerAppTheme {
                AppNavGraph()
            }
        }
    }
}