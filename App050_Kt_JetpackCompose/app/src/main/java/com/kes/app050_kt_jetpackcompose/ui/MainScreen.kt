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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kes.app050_kt_jetpackcompose.navigation.AppNavGraph
import com.kes.app050_kt_jetpackcompose.navigation.BottomBarNavigationState
import com.kes.app050_kt_jetpackcompose.navigation.rememberNavigationController
import com.kes.app050_kt_jetpackcompose.ui.composable.CommentsScreen
import com.kes.app050_kt_jetpackcompose.ui.composable.HomeScreen


@Composable
fun MainScreen() {
    val navController = rememberNavigationController()
    val navBackStackEntry by navController.navHostController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Log.d("TAG", "BottomAppBar Called")

                for (state in BottomBarNavigationState.asList()) {
                    val isSelected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == state.screen.route
                    } ?: false
                    NavigationBarItem(
                        selected = isSelected,
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
            favoriteScreenContent = { TextCounter("fav screen", modifier) },
            profileScreenContent = { TextCounter("profile screen", modifier) },
            postsScreenContent = {
                HomeScreen(
                    onCommentsClickListener = { post ->
                        navController.navigateToComments(post)
                    }
                )
            },
            commentsScreenContent = {post ->
                CommentsScreen(
                    postItem = post,
                    onBackPressed = {
                        navController.navHostController.popBackStack()
                    }
                )
            }

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
