package com.kes.app050_kt_jetpackcompose.ui.profileCard

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kes.app050_kt_jetpackcompose.R
import com.kes.app050_kt_jetpackcompose.ui.theme.ApplicationTheme


@Composable
fun ProfileCard(
    item: ProfileItem,
    onFollowClickListener: ((ProfileItem) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column {
            Row( // Top information row
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.ic_kotlin),
                    contentDescription = "Application logo.",
                    contentScale = ContentScale.Fit,
                )

                TextWithLabel(text = "6,901", label = "Posts")
                TextWithLabel(text = "196M", label = "Followers")
                TextWithLabel(text = "27", label = "Following")
            }

            Column(
                // Description
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 5.dp),
            ) {
                Text(
                    text = "Sidonia #${item.id}",
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Cursive,
                )
                Text(text = "#test", Modifier.padding(top = 4.dp))
                Text(text = "www.gogo.com", Modifier.padding(bottom = 4.dp))
                FollowButton(item.isFollowing) {
                    Log.d("TAG", "ProfileCard: button pressed")
                    onFollowClickListener?.invoke(item)
                }
            }
        }
    }
}

@Composable
fun FollowButton(
    isFollowing: Boolean,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = {
            Log.d("TAG", "FollowButton: button pressed")
            onClick?.invoke()
        },
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (!isFollowing) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        )
    ) {
        val text = if (isFollowing) {
            "Unfollow"
        } else {
            "Follow"
        }
        Text(text = text)
    }
}

@Composable
fun TextWithLabel(text: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text,
            fontSize = 32.sp,
            fontFamily = FontFamily.Cursive
        )
        Text(
            label,
            fontWeight = FontWeight.Bold,
        )
    }
}

private val testModel = ProfileItem(
    1,
    "test",
    false
)

@Preview
@Composable
private fun PreviewLight() {
    ApplicationTheme {
        ProfileCard(testModel)
    }
}

@Preview
@Composable
private fun PreviewDark() {
    ApplicationTheme(darkTheme = true) {
        ProfileCard(testModel.copy(isFollowing = true))
    }
}

@Composable
fun ProfileCardDismissBackground() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clip(RoundedCornerShape(10))

    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(Color.Green)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(Color.Red)
        )
    }
}