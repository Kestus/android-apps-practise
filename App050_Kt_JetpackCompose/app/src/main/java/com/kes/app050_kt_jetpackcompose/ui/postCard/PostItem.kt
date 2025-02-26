package com.kes.app050_kt_jetpackcompose.ui.postCard

import com.kes.app050_kt_jetpackcompose.R

data class PostItem(
    val communityName: String = "nameName",
    val communityImageResId: Int = R.drawable.ic_kotlin,
    val publicationDate: String = "13:37",
    val contentText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vestibulum efficitur lacus, a varius lectus consequat id. Sed eget posuere sem. Quisque faucibus tellus sit amet sem vulputate vulputate. Mauris diam ex, placerat at vestibulum a, ultrices id est. Aliquam erat volutpat. Integer a turpis massa. Nullam elementum massa nec est facilisis pulvinar. Proin ut tincidunt odio, nec condimentum mauris. Vestibulum eu nibh in felis vulputate cursus.",
    val contentImageResId: Int = R.drawable.post_image,
    val stats: List<StatsItem> = listOf(
        StatsItem(StatsType.VIEWS, 123),
        StatsItem(StatsType.SHARES, 200),
        StatsItem(StatsType.COMMENTS, 45),
        StatsItem(StatsType.LIKES, 800)
    )
) {
    fun formatContentText(): String {
        return if (contentText.length > 120) contentText.take(119) + "..."
        else contentText
    }


}