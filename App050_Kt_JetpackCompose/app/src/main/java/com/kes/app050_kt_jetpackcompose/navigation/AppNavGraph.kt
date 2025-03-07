package com.kes.app050_kt_jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem


@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    favoriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    postsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        homeScreenNavGraph(
            postsScreenContent = postsScreenContent,
            commentsScreenContent = commentsScreenContent
        )

        composable(Screen.Favourite.route) { favoriteScreenContent() }
        composable(Screen.Profile.route) { profileScreenContent() }
    }
}