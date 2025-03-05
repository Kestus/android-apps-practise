package com.kes.app050_kt_jetpackcompose.ui.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kes.app050_kt_jetpackcompose.R
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem
import com.kes.app050_kt_jetpackcompose.domain.postCard.StatsType

@Preview
@Composable
private fun PostCardPreview() {
    PostCard(
        modifier = Modifier.padding(10.dp),
        postItem = PostItem()
    )
}

typealias incrementStatListener = ((PostItem, StatsType) -> Unit)?
typealias OnCommentsClickListenerCallback = ((PostItem) -> Unit)?

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    postItem: PostItem,
    onViewsClickListener: incrementStatListener = null,
    onShareClickListener: incrementStatListener = null,
    onLikeClickListener: incrementStatListener = null,
    onCommentsClickListener: OnCommentsClickListenerCallback = null
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 6.dp,
                    start = 6.dp,
                    end = 6.dp
                ),
        ) {
            // Header
            Header(postItem)

            // Post body
            Column {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = postItem.formatContentText(),
                    fontSize = 18.sp
                )
                // Image if res not null
                postItem.bodyImageResId?.let {
                    Image(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(3))
                            .fillMaxWidth()
                            .heightIn(max = 420.dp),
                        painter = painterResource(id = it),
                        contentDescription = "post image",
                        contentScale = ContentScale.FillHeight
                    )
                }
            }

            // Footer
            Stats(
                postItem = postItem,
                onViewsClickListener = onViewsClickListener,
                onShareClickListener = onShareClickListener,
                onLikeClickListener = onLikeClickListener,
                onCommentsClickListener = onCommentsClickListener
            )
        }
    }
}


@Composable
private fun Header(postData: PostItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Author image
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(CircleShape),
            painter = painterResource(id = postData.communityImageResId),
            contentDescription = "Post Image"

        )
        Spacer(modifier = Modifier.width(6.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            // Author name
            Text(
                text = postData.communityName,
                fontSize = 20.sp
            )
            // Timestamp
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = postData.publicationDate,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
        // More button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxHeight()
                .width(44.dp),
            shape = RoundedCornerShape(20),
            contentPadding = PaddingValues(1.dp),
            colors = ButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Gray
            )

        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = "Show more",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun Stats(
    postItem: PostItem,
    onViewsClickListener: incrementStatListener = null,
    onShareClickListener: incrementStatListener = null,
    onLikeClickListener: incrementStatListener = null,
    onCommentsClickListener: OnCommentsClickListenerCallback = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CounterWithIcon(
            postItem.stats.views.toString(),
            R.drawable.ic_eye
        ) {
            onViewsClickListener?.invoke(postItem, StatsType.VIEW)
        }
        Spacer(modifier = Modifier.weight(1f))
        CounterWithIcon(
            postItem.stats.shares.toString(),
            R.drawable.ic_share
        ) {
            onShareClickListener?.invoke(postItem, StatsType.SHARE)
        }
        Spacer(modifier = Modifier.width(4.dp))
        CounterWithIcon(
            postItem.stats.comments.toString(),
            R.drawable.ic_comment
        ) {
            onCommentsClickListener?.invoke(postItem)
        }
        Spacer(modifier = Modifier.width(4.dp))
        CounterWithIcon(
            postItem.stats.likes.toString(),
            R.drawable.ic_fav_filled
        ) {
            onLikeClickListener?.invoke(postItem, StatsType.LIKE)
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@Composable
private fun CounterWithIcon(text: String, drawableId: Int, onClick: () -> Unit) {
    Button(
        onClick = { onClick.invoke() },
        contentPadding = PaddingValues(horizontal = 4.dp),
        shape = RoundedCornerShape(20),
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Gray
        )
    ) {
        Text(
            text = text,
        )
        Spacer(modifier = Modifier.width(3.dp))
        Icon(
            painter = painterResource(id = drawableId),
            contentDescription = "view count",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}