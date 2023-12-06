package com.vvz.brewbird.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
internal fun BottomTabFlow(startDestination: String,
                           builder: NavGraphBuilder.(controller: NavHostController) -> Unit) {

    val controller = rememberNavController()
    val currentDestination by controller.currentBackStackEntryAsState()
    val systemBarsState = LocalSystemBarStateProvider.current.apply { onBack = { controller.popBackStack() } }

    LaunchedEffect(currentDestination) {
        when (currentDestination?.destination?.route) {
            startDestination -> systemBarsState.showBackButton = false
            else             -> systemBarsState.showBackButton = true
        }
    }

    NavHost(navController = controller,
            startDestination = startDestination,
            builder = { builder(controller) },
            modifier = Modifier.fillMaxSize())

}