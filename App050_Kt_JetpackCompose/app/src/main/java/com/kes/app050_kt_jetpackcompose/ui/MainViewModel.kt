package com.kes.app050_kt_jetpackcompose.ui

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kes.app050_kt_jetpackcompose.R
import com.kes.app050_kt_jetpackcompose.domain.postCard.CommentItem
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItemStats
import com.kes.app050_kt_jetpackcompose.domain.postCard.StatsType
import com.kes.app050_kt_jetpackcompose.ui.state.HomeScreenState
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val initialState = HomeScreenState.Posts(generatePosts())

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState>
        get() = _screenState

    private var savedState: HomeScreenState? = null

    fun showComments(item: PostItem) = setState<HomeScreenState.Posts> {
        savedState = it
        val comments = generateComments(item)
        HomeScreenState.Comments(item, comments)
    }

    fun closeComments() = setState<HomeScreenState.Comments> {
        val newState = savedState ?: initialState
        savedState = null
        newState
    }

    fun incStat(item: PostItem, type: StatsType) =
        setState<HomeScreenState.Posts> { state ->
            val newStats = item.stats.inc(type)
            val newPosts = state.posts.map {
                if (it == item) it.copy(stats = newStats)
                else it
            }
            HomeScreenState.Posts(newPosts)
        }

    fun delete(postId: Int): Boolean {
        var isDeleted = false
        withState<HomeScreenState.Posts> { state ->
            val newPosts = state.posts.filter {
                if (it.id == postId) {
                    isDeleted = true
                    false
                } else true
            }
            _screenState.value = HomeScreenState.Posts(newPosts)
        }
        return isDeleted
    }

    private inline fun <reified T : HomeScreenState> setState(block: (T) -> HomeScreenState?) {
        withState<T> {
            block(it)?.let { newState ->
                _screenState.value = newState
            }
        }
    }

    private inline fun <reified T : HomeScreenState> withState(block: (T) -> Unit) {
        _screenState.value?.let {
            if (it is T) {
                block(it)
            } else throw IllegalStateException("wrong generic type for current screen state")
        }
    }

    private fun generateComments(item: PostItem): List<CommentItem> =
        mutableListOf<CommentItem>().apply {
            repeat(item.stats.comments) {
                val content = " comment content "
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