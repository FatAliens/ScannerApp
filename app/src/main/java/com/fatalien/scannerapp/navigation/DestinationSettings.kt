package com.fatalien.scannerapp.navigation

import com.fatalien.scannerapp.navigation.data.NavDestination

object DestinationSettings {
    val Destinations = NavDestination.entries.toList()
    val StartDestination = Destinations.first()
}