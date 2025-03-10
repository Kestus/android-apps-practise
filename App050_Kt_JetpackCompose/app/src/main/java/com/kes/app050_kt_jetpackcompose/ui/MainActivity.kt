package com.kes.app050_kt_jetpackcompose.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import com.kes.app050_kt_jetpackcompose.ui.composable.ActivityResultScreen
import com.kes.app050_kt_jetpackcompose.ui.theme.ApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        val path = MutableLiveData<String>()
        val intent = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        val contract = ActivityResultContracts.PickVisualMedia()
        val launcher = registerForActivityResult(contract) {
            it?.let {
                path.value = it.path
            }
        }



        setContent {
            ApplicationTheme {
//                MainScreen()
                ActivityResultScreen()
            }
        }

    }
}