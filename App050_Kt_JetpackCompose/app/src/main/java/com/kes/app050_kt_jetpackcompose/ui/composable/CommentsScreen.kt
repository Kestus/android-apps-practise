package com.kes.app050_kt_jetpackcompose.ui.composable

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kes.app050_kt_jetpackcompose.domain.postCard.CommentItem
import com.kes.app050_kt_jetpackcompose.domain.postCard.PostItem
import com.kes.app050_kt_jetpackcompose.ui.state.CommentsScreenState
import com.kes.app050_kt_jetpackcompose.ui.theme.ApplicationTheme
import com.kes.app050_kt_jetpackcompose.ui.viewModel.CommentsViewModel
import com.kes.app050_kt_jetpackcompose.ui.viewModel.CommentsViewModelFactory

private val colorTopBarContainer = Color(0xFFD7EEFF)
private val colorScaffoldContainer = Color(0xFFF3FAFF)

@Composable
fun CommentsScreen(
    post: PostItem,
    onBackPressed: () -> Unit,
) {
    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModelFactory(post)
    )
    val screenState = viewModel.screenState.observeAsState(CommentsScreenState.Initial)

    when (val currentState = screenState.value) {
        is CommentsScreenState.Comments -> {
            ListComments(
                postItem = currentState.post,
                comments = currentState.comments,
                onBackPressed = onBackPressed
            )
            BackHandler { onBackPressed() }
        }
        CommentsScreenState.Initial -> {/* do nothing */}
    }
}

@Composable
private fun ListComments(
    postItem: PostItem,
    comments: List<CommentItem>,
    onBackPressed: () -> Unit
) {
    Scaffold(
        containerColor = colorScaffoldContainer,
        topBar = { TopBar(postItem, onBackPressed) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = comments, key = { it.id }) {
                CommentContainer(it)
            }
            item {
                Spacer(
                    Modifier.windowInsetsBottomHeight(
                        WindowInsets.systemBars
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(postItem: PostItem, onBackPressed: () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .shadow(6.dp),
        colors = TopAppBarDefaults
            .topAppBarColors()
            .copy(
                containerColor = colorTopBarContainer
            ),
        navigationIcon = {
            IconButton(
                onClick = { onBackPressed() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Comments for Post #${postItem.id}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        },
    )
}

@Composable
private fun CommentContainer(item: CommentItem, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(4.dp)
    ) {
        Image(
            modifier = Modifier
                .size(40.dp),
            painter = painterResource(item.imageResId),
            contentDescription = null,
        )
        Spacer(Modifier.width(6.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Author: ${item.authorName}",
                fontWeight = FontWeight.Medium,
            )
            Spacer(Modifier.height(2.dp))
            Text(text = item.content)
            Spacer(Modifier.height(2.dp))
            Text(
                text = item.timestamp,
                color = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val post = PostItem()
    val comments = mutableListOf<CommentItem>().apply {
        repeat(20) {
            add(CommentItem(id = it))
        }
    }
    ApplicationTheme {
        ListComments(post, comments) {}
    }
}