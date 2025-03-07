package com.kes.app050_kt_jetpackcompose.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kes.app050_kt_jetpackcompose.domain.postCard.CommentItem
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem
import com.kes.app050_kt_jetpackcompose.ui.state.CommentsScreenState

class CommentsViewModel(
    post: PostItem
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState>
        get() = _screenState

    init {
        _screenState.value = CommentsScreenState.Comments(post, generateComments(post))
    }

    private inline fun <reified T : CommentsScreenState> setState(block: (T) -> CommentsScreenState?) {
        withState<T> {
            block(it)?.let { newState ->
                _screenState.value = newState
            }
        }
    }

    private inline fun <reified T : CommentsScreenState> withState(block: (T) -> Unit) {
        _screenState.value?.let {
            if (it is T) {
                block(it)
            } else throw IllegalStateException("wrong generic type for current screen state")
        }
    }

    private fun generateComments(item: PostItem): List<CommentItem> =
        mutableListOf<CommentItem>().apply {
            repeat(item.stats.comments) {
                val content = "comment content "
                add(
                    CommentItem(
                        id = it,
                        authorName = "#$it",
                        content = content.repeat(it + 3 % 10),
                        timestamp = "13:${37 + it}"
                    )
                )
            }
        }
}