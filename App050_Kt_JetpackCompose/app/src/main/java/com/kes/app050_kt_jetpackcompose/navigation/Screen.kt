package com.kes.app050_kt_jetpackcompose.navigation

sealed class Screen(
    val route: String
) {

    data object Home : Screen(ROUTE_HOME)
    data object Favourite : Screen(ROUTE_FAVORITE)
    data object Profile : Screen(ROUTE_PROFILE)

    data object Comments : Screen(ROUTE_COMMENTS)
    data object Posts : Screen(ROUTE_POSTS)

    companion object {
        private const val ROUTE_HOME = "route_home"
        private const val ROUTE_FAVORITE = "route_favorite"
        private const val ROUTE_PROFILE = "route_profile"

        private const val ROUTE_COMMENTS = "route_comments"
        private const val ROUTE_POSTS = "route_posts"
    }

}