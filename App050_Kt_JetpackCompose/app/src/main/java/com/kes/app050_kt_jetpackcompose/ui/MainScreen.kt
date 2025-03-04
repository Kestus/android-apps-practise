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
import com.kes.app050_kt_jetpackcompose.ui.composable.HomeScreen
import com.kes.app050_kt_jetpackcompose.ui.navigation.AppNavGraph
import com.kes.app050_kt_jetpackcompose.ui.navigation.BottomBarNavigationState
import com.kes.app050_kt_jetpackcompose.ui.navigation.rememberNavigationController


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavigationController()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Log.d("TAG", "BottomAppBar Called")

                for (state in BottomBarNavigationState.asList()) {
                    NavigationBarItem(
                        selected = navController.currentRoute == state.screen.route,
                        onClick = { navController.navigateTo(state.screen.route) },
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
            navHostController = navController.navHostController,
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
