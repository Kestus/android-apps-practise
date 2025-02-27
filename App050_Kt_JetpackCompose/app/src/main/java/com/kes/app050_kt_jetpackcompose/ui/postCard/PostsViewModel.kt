package com.kes.app050_kt_jetpackcompose.ui.postCard

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kes.app050_kt_jetpackcompose.R
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.random.Random

class PostsViewModel : ViewModel() {

    private val _allPosts: List<PostItem> = generatePosts()

    private val _postsLiveData = MutableLiveData(_allPosts)
    val postsLiveData: LiveData<List<PostItem>>
        get() = _postsLiveData

    private fun generatePosts(): MutableList<PostItem> = mutableListOf<PostItem>().apply {
        val random = Random(GregorianCalendar().get(Calendar.MINUTE))
        repeat(1000) {
            val imageRes = if (random.nextBoolean()) R.drawable.post_image
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

    fun incItemStat(item: PostItem, type: StatsType) {
        val newStats = item.stats.inc(type)
        _postsLiveData.value = _postsLiveData.value?.let { list ->
            list.map {
                if (it == item) it.copy(stats = newStats)
                else it
            }
        }
    }

    fun delete(postId: Int): Boolean {
        var isDeleted = false
        _postsLiveData.value = _postsLiveData.value?.let { list ->
            list.filter {
                if (it.id == postId) {
                    isDeleted = true
                    false
                } else true
            }
        }
        return isDeleted
    }
}