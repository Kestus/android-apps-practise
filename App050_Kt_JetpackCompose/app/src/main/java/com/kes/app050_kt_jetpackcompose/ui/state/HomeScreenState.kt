package com.kes.app050_kt_jetpackcompose.ui.state

import com.kes.app050_kt_jetpackcompose.domain.postCard.CommentItem
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem

sealed class HomeScreenState {
    data object Initial: HomeScreenState()

    data class Posts(val posts: List<PostItem>) : HomeScreenState()
    data class Comments(val post: PostItem, val comments: List<CommentItem>) : HomeScreenState()
}
