package com.kes.app050_kt_jetpackcompose.domain.postCard

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.kes.app050_kt_jetpackcompose.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostItem(
    val id: Int = 0,
    val communityName: String = "nameName",
    val communityImageResId: Int = R.drawable.ic_kotlin,
    val publicationDate: String = "13:37",
    val bodyText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vestibulum efficitur lacus, a varius lectus consequat id. Sed eget posuere sem. Quisque faucibus tellus sit amet sem vulputate vulputate. Mauris diam ex, placerat at vestibulum a, ultrices id est. Aliquam erat volutpat. Integer a turpis massa. Nullam elementum massa nec est facilisis pulvinar. Proin ut tincidunt odio, nec condimentum mauris. Vestibulum eu nibh in felis vulputate cursus.",
    val bodyImageResId: Int? = null,
    val stats: PostItemStats = PostItemStats()
): Parcelable {
    fun formatContentText(): String {
        return if (bodyText.length > 120) bodyText.take(119) + "..."
        else bodyText
    }

    companion object {

        val NavigationType = object : NavType<PostItem>(false) {
            override val name: String
                get() = "post_item"

            private val gson = Gson()

            override fun put(bundle: Bundle, key: String, value: PostItem) {
                bundle.putParcelable(key, value)
            }

            override fun get(bundle: Bundle, key: String): PostItem? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, PostItem::class.java)
                } else {
                    bundle.getParcelable(key)
                }
            }

            override fun parseValue(value: String): PostItem {
                return gson.fromJson(value, PostItem::class.java)
            }
        }

    }
}