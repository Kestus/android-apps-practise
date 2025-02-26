package com.kes.app050_kt_jetpackcompose.domain

enum class StatsType {
    VIEWS, COMMENTS, SHARES, LIKES
}

data class StatsItem(
    val type: StatsType,
    val count: Int = 0
) {

}

fun List<StatsItem>.getItemByType(requiredType: StatsType): StatsItem {
    for (item in this) {
        if (item.type == requiredType) return item
    }
    throw IllegalStateException()
}