package com.fatalien.scannerapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fatalien.scannerapp.navigation.data.NavDestination
import com.fatalien.scannerapp.screens.order.OrderScreen
import com.fatalien.scannerapp.screens.order.OrderScreenVM
import com.fatalien.scannerapp.screens.product.ProductsScreen
import com.fatalien.scannerapp.screens.product.ProductsScreenVM

@Composable
fun AppNavGraph(navHostController: NavHostController = rememberNavController()) {
    NavHost(navHostController, startDestination = DestinationSettings.StartDestination.route) {
        DestinationSettings.Destinations.forEach { destination ->
            when (destination) {
                NavDestination.ProductsScreen -> {
                    composable(destination.route) {
                        val vm = hiltViewModel<ProductsScreenVM>()
                        ProductsScreen(vm, navHostController)
                    }
                }

                NavDestination.OrderScreen -> {
                    composable(NavDestination.OrderScreen.route) {
                        val vm = hiltViewModel<OrderScreenVM>()
                        OrderScreen(vm, navHostController)
                    }
                }
            }
        }
    }
}