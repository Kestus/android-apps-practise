package com.kes.app050_kt_jetpackcompose.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kes.app050_kt_jetpackcompose.ui.MainViewModel
import com.kes.app050_kt_jetpackcompose.ui.postCard.PostCard


@Composable
fun HomeScreen(modifier: Modifier, viewModel: MainViewModel) {
    val posts = viewModel.postsLiveData.observeAsState(listOf())
    LazyColumn(
        modifier = modifier,
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