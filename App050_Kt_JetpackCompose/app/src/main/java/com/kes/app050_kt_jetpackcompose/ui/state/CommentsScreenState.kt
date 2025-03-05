package com.kes.app050_kt_jetpackcompose.ui.state

import com.kes.app050_kt_jetpackcompose.domain.postCard.CommentItem
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem

sealed class CommentsScreenState {

    data object Initial : CommentsScreenState()

    data class Comments(val post: PostItem, val comments: List<CommentItem>) : CommentsScreenState()

}