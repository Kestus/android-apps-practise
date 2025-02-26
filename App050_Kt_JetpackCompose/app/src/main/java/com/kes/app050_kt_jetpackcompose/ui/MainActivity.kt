package com.kes.app050_kt_jetpackcompose.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.kes.app050_kt_jetpackcompose.ui.profileCard.ProfileCard
import com.kes.app050_kt_jetpackcompose.ui.profileCard.ProfileCardDismissBackground
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
                Scaffold { paddingValues ->
                    val profiles = viewModel.profiles.observeAsState(listOf())
                    LazyColumn(
                        modifier = Modifier.padding(paddingValues),
                    ) {
                        items(profiles.value, key = { it.id }) { profile ->
                            val dismissState = rememberSwipeToDismissBoxState(
                                positionalThreshold = { it * 0.3f },
                                confirmValueChange = {
                                    when (it) {
                                        SwipeToDismissBoxValue.EndToStart -> {
                                            Log.d("TAG", "onCreate: deleting profile...")
                                            viewModel.delete(profile)
                                            true
                                        }

                                        else -> false
                                    }
                                }
                            )
                            SwipeToDismissBox(
                                modifier = Modifier.animateItem(),
                                state = dismissState,
                                backgroundContent = { ProfileCardDismissBackground() },
                                enableDismissFromStartToEnd = false,
                            ) {
                                ProfileCard(profile) {
                                    viewModel.toggleFollowStatus(profile)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}