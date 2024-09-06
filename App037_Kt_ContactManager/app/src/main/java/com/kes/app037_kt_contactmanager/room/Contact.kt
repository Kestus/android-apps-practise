package com.kes.app037_kt_contactmanager.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    val id: Int,

    @ColumnInfo(name = "contact_name")
    var name: String,

    @ColumnInfo(name = "contact_email")
    var email: String
)