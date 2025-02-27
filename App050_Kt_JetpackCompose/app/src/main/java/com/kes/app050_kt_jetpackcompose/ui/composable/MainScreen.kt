package com.kes.app050_kt_jetpackcompose.ui.composable

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kes.app050_kt_jetpackcompose.ui.postCard.PostCard
import com.kes.app050_kt_jetpackcompose.ui.postCard.PostsViewModel
import com.kes.app050_kt_jetpackcompose.ui.state.NavigationState


@Composable
fun MainScreen(viewModel: PostsViewModel) {
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
        val posts = viewModel.postsLiveData.observeAsState(listOf())
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(
                vertical = 12.dp,
                horizontal = 6.dp
            ),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(posts.value, key = { it.id }) { postItem ->
                val swipeState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        when (it) {
                            SwipeToDismissBoxValue.EndToStart -> viewModel.delete(
                                postItem.id
                            )

                            else -> false
                        }
                    },
                    positionalThreshold = { it * 0.7f }
                )
                SwipeToDismissBox(
                    modifier = Modifier.animateItem(),
                    state = swipeState,
                    backgroundContent = {}
                ) {
                    PostCard(
                        postItem = postItem,
                        onViewsClickListener = viewModel::incItemStat,
                        onShareClickListener = viewModel::incItemStat,
                        onLikeClickListener = viewModel::incItemStat,
                        onCommentsClickListener = viewModel::incItemStat
                    )
                }
            }
        }
    }
}
