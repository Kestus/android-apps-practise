package com.kes.app050_kt_jetpackcompose.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kes.app050_kt_jetpackcompose.domain.PostItem
import com.kes.app050_kt_jetpackcompose.domain.StatsItem

class MainViewModel: ViewModel() {

    private val _postItem = MutableLiveData(PostItem())
    val postItem: LiveData<PostItem> get() = _postItem

    fun incStatCount(item: StatsItem) {
        _postItem.value.let {
            val oldStats = it?.stats ?: throw IllegalStateException()
            val newStats = oldStats.map { stat ->
                if (stat.type == item.type) {
                    val newCount = stat.count.inc()
                    stat.copy(count = newCount)
                } else {
                    stat
                }
            }
            _postItem.value = it.copy(stats = newStats)
        }
    }
}