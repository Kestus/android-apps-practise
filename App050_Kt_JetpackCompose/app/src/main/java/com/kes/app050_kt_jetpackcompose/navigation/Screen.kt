package com.kes.app050_kt_jetpackcompose.navigation

import android.net.Uri
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem

sealed class Screen(
    val route: String
) {

    data object Home : Screen(ROUTE_HOME)
    data object Favourite : Screen(ROUTE_FAVORITE)
    data object Profile : Screen(ROUTE_PROFILE)

    data object Comments : Screen(ROUTE_COMMENTS) {

        const val BASE_ROUTE = "comments"
        const val KEY_POST_ID = "post_id"

        fun getRouteWithArgs(postItem: PostItem): String {
            return BASE_ROUTE + "/" + postItem.id
        }
    }
    data object Posts : Screen(ROUTE_POSTS)

    companion object {
        private const val ROUTE_HOME = "route_home"
        private const val ROUTE_FAVORITE = "route_favorite"
        private const val ROUTE_PROFILE = "route_profile"

        private const val ROUTE_COMMENTS = "${Comments.BASE_ROUTE}/{${Comments.KEY_POST_ID}}"
        private const val ROUTE_POSTS = "route_posts"
    }
}

fun String.encode() = Uri.encode(this)