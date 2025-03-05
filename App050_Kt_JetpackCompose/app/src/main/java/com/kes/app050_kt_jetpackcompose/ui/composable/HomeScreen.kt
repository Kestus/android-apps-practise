package com.kes.app050_kt_jetpackcompose.ui.composable

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.kes.app050_kt_jetpackcompose.ui.MainViewModel
import com.kes.app050_kt_jetpackcompose.ui.state.HomeScreenState


@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val screenState by viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when (val currentState = screenState) {
        is HomeScreenState.Posts ->
            PostsScreen(
                currentState.posts,
                viewModel
            )

        is HomeScreenState.Comments -> {
            CommentsScreen(
                currentState.post,
                currentState.comments,
                viewModel::closeComments
            )
            BackHandler { viewModel.closeComments() }
        }
        HomeScreenState.Initial -> {/* do nothing */}
    }

}

