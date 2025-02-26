package com.kes.app050_kt_jetpackcompose.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.kes.app050_kt_jetpackcompose.ui.composable.MainScreen
import com.kes.app050_kt_jetpackcompose.ui.theme.ApplicationTheme
import com.kes.app050_kt_jetpackcompose.ui.viewModel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            ApplicationTheme {
                MainScreen(viewModel)
            }
        }
    }
}