package com.kes.app050_kt_jetpackcompose.ui.postCard

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

@Preview
@Composable
private fun PostCardPreview() {
    PostCard(
        modifier = Modifier.padding(10.dp),
        postItem = PostItem()
    )
}

private typealias clickListener = ((StatsItem) -> Unit)?

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    postItem: PostItem,
    onViewsClickListener: clickListener = null,
    onShareClickListener: clickListener = null,
    onLikeClickListener: clickListener = null,
    onCommentsClickListener: clickListener = null
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

            // Post content
            Column {
                // Body
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = postItem.formatContentText(),
                    fontSize = 18.sp
                )
                // Image
                Image(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(3))
                        .fillMaxWidth()
                        .heightIn(max = 420.dp),
                    painter = painterResource(id = postItem.contentImageResId),
                    contentDescription = "post image",
                    contentScale = ContentScale.FillHeight
                )
            }

            // Footer
            Stats(
                stats = postItem.stats,
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
    stats: List<StatsItem>,
    onViewsClickListener: clickListener = null,
    onShareClickListener: clickListener = null,
    onLikeClickListener: clickListener = null,
    onCommentsClickListener: clickListener = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val itemViews = stats.getItemByType(StatsType.VIEWS)
        val itemShares = stats.getItemByType(StatsType.SHARES)
        val itemComments = stats.getItemByType(StatsType.COMMENTS)
        val itemLikes = stats.getItemByType(StatsType.LIKES)

        CounterWithIcon(
            itemViews.count.toString(),
            R.drawable.ic_eye
        ) {
            onViewsClickListener?.invoke(itemViews)
        }
        Spacer(modifier = Modifier.weight(1f))
        CounterWithIcon(
            itemShares.count.toString(),
            R.drawable.ic_share
        ) {
            onShareClickListener?.invoke(itemShares)
        }
        Spacer(modifier = Modifier.width(4.dp))
        CounterWithIcon(
            itemComments.count.toString(),
            R.drawable.ic_comment
        ) {
            onCommentsClickListener?.invoke(itemComments)
        }
        Spacer(modifier = Modifier.width(4.dp))
        CounterWithIcon(
            itemLikes.count.toString(),
            R.drawable.ic_fav_filled
        ) {
            onLikeClickListener?.invoke(itemLikes)
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