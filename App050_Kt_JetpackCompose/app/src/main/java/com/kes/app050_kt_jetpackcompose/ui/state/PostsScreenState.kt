package com.kes.app050_kt_jetpackcompose.ui.state

import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem

sealed class PostsScreenState {
    data object Initial: PostsScreenState()

    data class Posts(val posts: List<PostItem>) : PostsScreenState()

}
