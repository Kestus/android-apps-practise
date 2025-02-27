package com.kes.app050_kt_jetpackcompose.ui.postCard

data class PostItemStats(
    val views: Int = 0,
    val comments: Int = 0,
    val shares: Int = 0,
    val likes: Int = 0
) {
    // returns this class with stat of given type incremented by 1
    fun inc(type: StatsType): PostItemStats {
        return when (type) {
            StatsType.VIEW -> copy(views = views + 1)
            StatsType.COMMENT -> copy(comments = comments + 1)
            StatsType.SHARE -> copy(shares = shares + 1)
            StatsType.LIKE -> copy(likes = likes + 1)
        }
    }
}