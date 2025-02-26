package com.kes.app050_kt_jetpackcompose.ui.composable

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kes.app050_kt_jetpackcompose.domain.PostItem
import com.kes.app050_kt_jetpackcompose.ui.state.NavigationState
import com.kes.app050_kt_jetpackcompose.ui.viewModel.MainViewModel


@Composable
fun MainScreen(viewModel: MainViewModel) {
    Log.d("TAG", "MainScreen Called")
    val postItem = viewModel.postItem.observeAsState(PostItem())

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Log.d("TAG", "BottomAppBar Called")

                val selectedItemPosition = remember {
                    mutableIntStateOf(0)
                }

                val items = listOf(
                    NavigationState.Home,
                    NavigationState.Favourite,
                    NavigationState.Profile,
                )
                for (i in items.indices) {
                    val item = items[i]
                    NavigationBarItem(
                        selected = selectedItemPosition.intValue == i,
                        onClick = { selectedItemPosition.intValue = i },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(stringResource(id = item.titleResId))
                        },
                    )
                }
            }
        }
    ) { paddingValues ->
        PostCard(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 4.dp),
            postItem = postItem.value,
            onCommentsClickListener = viewModel::incStatCount,
            onLikeClickListener = viewModel::incStatCount,
            onShareClickListener = viewModel::incStatCount,
            onViewsClickListener = viewModel::incStatCount,
        )
    }
}
