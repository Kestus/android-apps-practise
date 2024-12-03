package com.kes.app047_kt_providers

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val permissionGranted = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
        if (permissionGranted) {
            fetchContacts()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_CONTACTS),
            READ_CONTACTS_RC
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_CONTACTS_RC) {
            val permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (permissionGranted) {
                fetchContacts()
            } else {
                Log.d("TAG_MainActivity", "Permission Denied!")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun fetchContacts() {
        thread {
            val cursor = contentResolver.query(
                Contacts.CONTENT_URI,
                null,
                null,
                null,
                null,
                null,
            )
            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Contacts.DISPLAY_NAME))
                val contact = Contact(id, name)
                Log.d("TAG", "fetchContacts: $contact")
            }
            cursor?.close()
        }
    }

    private fun fetchShoppingList() {
        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.kes.app041_kt_shoppinglist/items"),
                null,
                null,
                null,
                null,
                null,
            )
            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getString(cursor.getColumnIndexOrThrow("count"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0
                Log.d("TAG", "content: {$id, $name, $count, $enabled}")
            }
            cursor?.close()
        }
    }

    companion object {
        private const val READ_CONTACTS_RC = 100
    }
}