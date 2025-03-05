package com.kes.app050_kt_jetpackcompose.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem
import com.kes.app050_kt_jetpackcompose.ui.MainViewModel

@Composable
fun PostsScreen(
    posts: List<PostItem>,
    viewModel: MainViewModel,
    contentPadding: PaddingValues = PaddingValues(
        vertical = 12.dp,
        horizontal = 6.dp
    )
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            items(items = posts, key = { it.id }) { postItem ->
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
                        onViewsClickListener = viewModel::incStat,
                        onShareClickListener = viewModel::incStat,
                        onLikeClickListener = viewModel::incStat,
                        onCommentsClickListener = viewModel::showComments
                    )
                }
            }
            item {
                Spacer(
                    Modifier.windowInsetsBottomHeight(
                        WindowInsets.systemBars
                    )
                )
            }
        }
    }
}