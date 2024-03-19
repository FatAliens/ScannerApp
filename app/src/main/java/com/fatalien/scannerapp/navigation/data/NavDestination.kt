package com.fatalien.scannerapp.navigation.data

import android.graphics.drawable.VectorDrawable
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.fatalien.scannerapp.R

enum class NavDestination(
    val route: String,
    val iconRes: Int,
    val title: String,
    val description: String
) {
    ProductsScreen("products", R.drawable.fact_check, "Инвентаризация", "Products"),
    OrderScreen("order", R.drawable.list_alt, "Сборка заказа", "Order")
}