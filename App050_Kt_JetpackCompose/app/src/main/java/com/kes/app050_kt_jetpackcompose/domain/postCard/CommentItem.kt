package com.kes.app050_kt_jetpackcompose.domain.postCard

import com.kes.app050_kt_jetpackcompose.R

data class CommentItem(
    val id: Int,
    val authorName: String = "NoName",
    val imageResId: Int = R.drawable.image_comment_author,
    val content: String = "comment text placeholder",
    val timestamp: String = "00:00"
)