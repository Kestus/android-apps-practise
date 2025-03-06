package com.kes.app050_kt_jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationController(
    val navHostController: NavHostController
) {
    private val currentRoute
        get() = navHostController.currentBackStackEntry?.destination?.route


    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            // delete from backstack all routes up to start route
            popUpTo(navHostController.graph.findStartDestination().id) {
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

    fun navigateToComments() {
        navHostController.navigate(Screen.Comments.route)
    }
}

@Composable
fun rememberNavigationController(
    navHostController: NavHostController = rememberNavController()
): NavigationController = remember {
    NavigationController(navHostController)
}
