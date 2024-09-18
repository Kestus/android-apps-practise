package com.kes.app040_kt_journal.models

import android.text.format.DateUtils
import com.google.firebase.Timestamp
import java.util.Date


data class Journal(
    val title: String = "",
    val body: String = "",
    val imageUrl: String = "",

    val userID: String = "",
    val username: String = "",
    val timeAdded: Timestamp? = null,
) {
    fun getTimeString(): String {
        return Date(timeAdded!!.seconds).toString()
    }
}
