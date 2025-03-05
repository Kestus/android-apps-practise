package com.kes.app050_kt_jetpackcompose.navigation

sealed class Screen(
    val route: String
) {

    data object Home : Screen(ROUTE_HOME)
    data object Favourite : Screen(ROUTE_FAVORITE)
    data object Profile : Screen(ROUTE_PROFILE)

    companion object {
        private const val ROUTE_HOME = "route_home"
        private const val ROUTE_FAVORITE = "route_favorite"
        private const val ROUTE_PROFILE = "route_profile"
    }

}