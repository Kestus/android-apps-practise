package com.kes.app050_kt_jetpackcompose.domain.postCard

import com.kes.app050_kt_jetpackcompose.R

data class PostItem(
    val id: Int = 0,
    val communityName: String = "nameName",
    val communityImageResId: Int = R.drawable.ic_kotlin,
    val publicationDate: String = "13:37",
    val bodyText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vestibulum efficitur lacus, a varius lectus consequat id. Sed eget posuere sem. Quisque faucibus tellus sit amet sem vulputate vulputate. Mauris diam ex, placerat at vestibulum a, ultrices id est. Aliquam erat volutpat. Integer a turpis massa. Nullam elementum massa nec est facilisis pulvinar. Proin ut tincidunt odio, nec condimentum mauris. Vestibulum eu nibh in felis vulputate cursus.",
    val bodyImageResId: Int? = null,
    val stats: PostItemStats = PostItemStats()
) {
    fun formatContentText(): String {
        return if (bodyText.length > 120) bodyText.take(119) + "..."
        else bodyText
    }

}