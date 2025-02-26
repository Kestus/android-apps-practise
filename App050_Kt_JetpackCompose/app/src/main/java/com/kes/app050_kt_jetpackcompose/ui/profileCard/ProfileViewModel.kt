package com.kes.app050_kt_jetpackcompose.ui.profileCard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class ProfileViewModel : ViewModel() {

    private val profileList = mutableListOf<ProfileItem>().apply {
        repeat(500) {
            add(
                ProfileItem(
                    it,
                    "title: $it",
                    isFollowing = Random.nextBoolean()
                )
            )
        }
    }

    private val _profiles = MutableLiveData<List<ProfileItem>>(profileList)
    val profiles: LiveData<List<ProfileItem>> get() = _profiles

    fun toggleFollowStatus(model: ProfileItem) {
        val modifiedList = _profiles.value?.toMutableList() ?: mutableListOf()
        modifiedList.replaceAll {
            if (it == model) {
                it.copy(isFollowing = !it.isFollowing)
            } else it
        }
        _profiles.value = modifiedList
    }

}