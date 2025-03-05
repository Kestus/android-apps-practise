package com.kes.app050_kt_jetpackcompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.kes.app050_kt_jetpackcompose.R

sealed class NavigationState(
    val screen: Screen,
)

sealed class BottomBarNavigationState(
    screen: Screen,
    val titleResId: Int,
    val icon: ImageVector
) : NavigationState(screen) {

    companion object {
        fun asList() = listOf(
            Home, Favourite, Profile
        )
    }

    data object Home : BottomBarNavigationState(
        screen = Screen.Home,
        titleResId = R.string.nav_item_main,
        icon = Icons.Outlined.Home
    )

    data object Favourite : BottomBarNavigationState(
        screen = Screen.Favourite,
        titleResId = R.string.nav_item_fav,
        icon = Icons.Default.Favorite
    )

    data object Profile : BottomBarNavigationState(
        screen = Screen.Profile,
        titleResId = R.string.nav_item_profile,
        icon = Icons.Outlined.Person
    )
}



