package com.kes.app050_kt_jetpackcompose.ui.viewModel

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kes.app050_kt_jetpackcompose.R
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItemStats
import com.kes.app050_kt_jetpackcompose.domain.postCard.StatsType
import com.kes.app050_kt_jetpackcompose.ui.state.PostsScreenState
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.random.Random

class PostsViewModel : ViewModel() {

    private val _screenState = MutableLiveData<PostsScreenState>(PostsScreenState.Initial)
    val screenState: LiveData<PostsScreenState>
        get() = _screenState

    init {
        _screenState.value = PostsScreenState.Posts(generatePosts())
    }

    fun incStat(item: PostItem, type: StatsType) =
        setState<PostsScreenState.Posts> { state ->
            val newStats = item.stats.inc(type)
            val newPosts = state.posts.map {
                if (it == item) it.copy(stats = newStats)
                else it
            }
            PostsScreenState.Posts(newPosts)
        }

    fun delete(postId: Int): Boolean {
        var isDeleted = false
        withState<PostsScreenState.Posts> { state ->
            val newPosts = state.posts.filter {
                if (it.id == postId) {
                    isDeleted = true
                    false
                } else true
            }
            _screenState.value = PostsScreenState.Posts(newPosts)
        }
        return isDeleted
    }

    private inline fun <reified T : PostsScreenState> setState(block: (T) -> PostsScreenState?) {
        withState<T> {
            block(it)?.let { newState ->
                _screenState.value = newState
            }
        }
    }

    private inline fun <reified T : PostsScreenState> withState(block: (T) -> Unit) {
        _screenState.value?.let {
            if (it is T) {
                block(it)
            } else throw IllegalStateException("wrong generic type for current screen state")
        }
    }

    private fun generatePosts(): List<PostItem> =
        mutableListOf<PostItem>().apply {
            val random = Random(GregorianCalendar().get(Calendar.MINUTE))
            repeat(1000) {
                val imageRes = if (random.nextBoolean()) R.drawable.image_post
                else null
                add(
                    PostItem(
                        id = it,
                        communityName = "community #$it",
                        communityImageResId = R.drawable.ic_kotlin,
                        publicationDate = "13:${37 + it}",
                        bodyText = LoremIpsum(10 + it % 20).values.first(),
                        bodyImageResId = imageRes,
                        stats = PostItemStats(
                            196 + it,
                            10 + it,
                            4 + it,
                            69 + it
                        )
                    )
                )
            }
        }
}