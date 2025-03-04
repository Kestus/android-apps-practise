package com.kes.app050_kt_jetpackcompose.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kes.app050_kt_jetpackcompose.ui.composable.HomeScreen
import com.kes.app050_kt_jetpackcompose.ui.navigation.AppNavGraph
import com.kes.app050_kt_jetpackcompose.ui.navigation.NavigationState
import com.kes.app050_kt_jetpackcompose.ui.navigation.Screen


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Log.d("TAG", "BottomAppBar Called")
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val navStates = listOf(
                    NavigationState.Home,
                    NavigationState.Favourite,
                    NavigationState.Profile,
                )
                for (state in navStates) {
                    NavigationBarItem(
                        selected = currentRoute == state.screen.route,
                        onClick = {
                            navHostController.navigate(state.screen.route) {
                                // if not at Home route: navigate to Home route at back action
                                // else if backstack not empty: navigate to previous route
                                // else: close app
                                if (state != NavigationState.Home) {
                                    // delete from backstack all routes up to Home
                                    popUpTo(Screen.Home.route) {
                                        // save state of all deleted routes
                                        saveState = true
                                    }
                                }
                                // only one copy of instance will exist
                                launchSingleTop = true
                                // Navigating to the current route again, will reset it's state.
                                if (state.screen.route != currentRoute) {
                                    // restore route state, if it was previously deleted.
                                    // cant restore state after back action.
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = state.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(stringResource(id = state.titleResId))
                        },
                    )
                }
            }
        }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        AppNavGraph(
            navHostController = navHostController,
            homeScreenContent = { HomeScreen(modifier, viewModel) },
            favoriteScreenContent = { TextCounter("fav screen", modifier) },
            profileScreenContent = { TextCounter("profile screen", modifier) }
        )
    }
}

@Composable
fun TextCounter(text: String, modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableIntStateOf(0) }
    Text(
        modifier = modifier.clickable { count++ },
        text = "count: $count -- $text",
    )
}
