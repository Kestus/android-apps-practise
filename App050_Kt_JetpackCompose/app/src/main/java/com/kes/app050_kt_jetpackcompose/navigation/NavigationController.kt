package com.kes.app050_kt_jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationController(
    val navHostController: NavHostController
) {

    private val startRoute by lazy {
        navHostController.graph.startDestinationRoute
            ?: BottomBarNavigationState.Home.screen.route
    }

    val currentRoute
        get() = navHostController.currentBackStackEntry?.destination?.route

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            // delete from backstack all routes up to start route
            popUpTo(startRoute) {
                // save state of all deleted routes
                saveState = true
            }

            // only one copy of instance will exist
            launchSingleTop = true

            // Navigating to the current route again, will reset it's state.
            if (route != currentRoute) {
                // restore route state, if it was previously deleted.
                // cant restore state after back action.
                restoreState = true
            }
        }
    }
}

@Composable
fun rememberNavigationController(
    navHostController: NavHostController = rememberNavController()
): NavigationController = remember {
    NavigationController(navHostController)
}
