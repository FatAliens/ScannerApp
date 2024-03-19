package com.fatalien.scannerapp.navigation.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fatalien.scannerapp.navigation.DestinationSettings

@Composable
fun AppBottomNavBar(
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar {
        DestinationSettings.Destinations.forEach { destination ->
            NavigationBarItem(
                selected = (backStackEntry?.destination?.route
                    ?: DestinationSettings.StartDestination.route) == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(ImageVector.vectorResource(id = destination.iconRes), contentDescription = destination.description) },
                label = { Text(destination.title) },
            )
        }
    }
}