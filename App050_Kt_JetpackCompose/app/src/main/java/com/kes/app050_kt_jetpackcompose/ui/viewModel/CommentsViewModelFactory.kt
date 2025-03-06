package com.kes.app050_kt_jetpackcompose.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem

@Suppress("UNCHECKED_CAST")
class CommentsViewModelFactory(private val post: PostItem): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(post) as T
    }
}