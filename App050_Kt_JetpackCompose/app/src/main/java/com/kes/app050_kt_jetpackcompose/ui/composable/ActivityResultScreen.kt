package com.kes.app050_kt_jetpackcompose.ui.composable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.compose.AsyncImage


@Composable
fun ActivityResultScreen() {

    var imageUri by remember {
        mutableStateOf(Uri.EMPTY)
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            imageUri = it
        }
    )
    val contentType = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

    Scaffold { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFCEEFFF)),
                model = imageUri,
                contentDescription = null
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    launcher.launch(contentType)
                }
            ) {
                Text("Select Image")
            }
        }
    }
}