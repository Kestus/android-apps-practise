package com.kes.app050_kt_jetpackcompose.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState
import com.kes.app050_kt_jetpackcompose.ui.profileCard.ProfileCard
import com.kes.app050_kt_jetpackcompose.ui.profileCard.ProfileViewModel
import com.kes.app050_kt_jetpackcompose.ui.theme.ApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            ApplicationTheme {
                val profiles = viewModel.profiles.observeAsState(listOf())
                LazyColumn {
                    items(profiles.value) {profile ->
                        ProfileCard(profile) {
                            viewModel.toggleFollowStatus(profile)
                        }
                    }
                }
            }
        }
    }
}