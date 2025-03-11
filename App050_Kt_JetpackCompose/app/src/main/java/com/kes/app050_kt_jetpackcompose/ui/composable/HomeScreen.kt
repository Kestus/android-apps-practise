package com.kes.app050_kt_jetpackcompose.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kes.app050_kt_jetpackcompose.ui.state.PostsScreenState
import com.kes.app050_kt_jetpackcompose.ui.viewModel.PostsViewModel


@Composable
fun HomeScreen(
    onCommentsClickListener: OnCommentsClickListenerCallback
) {
    val viewModel: PostsViewModel = viewModel()
    val screenState by viewModel.screenState.observeAsState(PostsScreenState.Initial)

    when (val currentState = screenState) {
        is PostsScreenState.Posts ->
            PostsScreen(
                posts = currentState.posts,
                viewModel = viewModel,
                onCommentsClickListener = onCommentsClickListener
            )

        PostsScreenState.Initial -> {/* do nothing */}
    }

}

