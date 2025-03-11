package com.kes.app050_kt_jetpackcompose.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
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
                    type = PostItem.NavigationType
                }
            )
        ) {
            // comments/{post_id}
            val postItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.arguments?.getParcelable(Screen.Comments.KEY_POST_ITEM, PostItem::class.java)
            } else {
                it.arguments?.getParcelable(Screen.Comments.KEY_POST_ITEM)
            } ?: throw RuntimeException("postItem should not be null")
            commentsScreenContent(postItem)
        }
    }
}
