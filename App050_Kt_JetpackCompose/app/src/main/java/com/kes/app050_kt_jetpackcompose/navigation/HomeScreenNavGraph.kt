package com.kes.app050_kt_jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem


fun NavGraphBuilder.homeScreenNavGraph(
    postsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (PostItem) -> Unit
) {
    navigation(
        route = Screen.Home.route,
        startDestination = Screen.Posts.route,
    ) {
        composable(Screen.Posts.route) { postsScreenContent() }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.Comments.KEY_POST_ITEM) {
                    type = NavType.StringType
                }
            )
        ) {
            // comments/{post_id}
            val postJson = it.arguments?.getString(Screen.Comments.KEY_POST_ITEM) ?: ""
            val postItem = Gson().fromJson(postJson, PostItem::class.java)
            commentsScreenContent(postItem)
        }
    }
}
