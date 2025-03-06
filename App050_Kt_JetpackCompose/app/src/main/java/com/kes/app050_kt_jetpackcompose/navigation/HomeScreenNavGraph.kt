package com.kes.app050_kt_jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation


fun NavGraphBuilder.homeScreenNavGraph(
    postsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit
) {
    navigation(
        route = Screen.Home.route,
        startDestination = Screen.Posts.route,
    ) {
        composable(Screen.Posts.route) { postsScreenContent() }
        composable(Screen.Comments.route) { commentsScreenContent() }
    }
}