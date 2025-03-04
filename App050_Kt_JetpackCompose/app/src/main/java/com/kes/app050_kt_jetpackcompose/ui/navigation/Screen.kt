package com.kes.app050_kt_jetpackcompose.ui.navigation

sealed class Screen(
    val route: String
) {

    data object Home : Screen(ROUTE_HOME)
    data object Favourite : Screen(ROUTE_FAVORITE)
    data object Profile : Screen(ROUTE_PROFILE)

    companion object {
        private const val ROUTE_HOME = "news_feed"
        private const val ROUTE_FAVORITE = "favorite"
        private const val ROUTE_PROFILE = "profile"

        val VALUES = listOf(
            Home, Favourite, Profile
        )
    }

}