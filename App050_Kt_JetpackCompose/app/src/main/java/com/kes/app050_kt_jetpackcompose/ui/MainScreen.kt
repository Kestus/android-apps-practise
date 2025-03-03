package com.kes.app050_kt_jetpackcompose.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kes.app050_kt_jetpackcompose.ui.composable.HomeScreen
import com.kes.app050_kt_jetpackcompose.ui.state.NavigationState


@Composable
fun MainScreen(viewModel: MainViewModel) {

    val selectedNavState by viewModel.selectedNavItem.observeAsState(NavigationState.Home)
    val navigationStates = listOf(
        NavigationState.Home,
        NavigationState.Favourite,
        NavigationState.Profile,
    )

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Log.d("TAG", "BottomAppBar Called")

                for (state in navigationStates) {
                    NavigationBarItem(
                        selected = selectedNavState == state,
                        onClick = { viewModel.selectNavState(state) },
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
        when (selectedNavState) {
            NavigationState.Home -> {
                HomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    viewModel
                )
            }
            NavigationState.Favourite -> {
                Text("Favorite", modifier = Modifier.padding(paddingValues))
            }
            NavigationState.Profile -> {
                Text("Profile", modifier = Modifier.padding(paddingValues))
            }
        }

    }
}
