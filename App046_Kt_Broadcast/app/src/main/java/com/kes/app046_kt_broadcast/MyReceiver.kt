package com.kes.app046_kt_broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BATTERY_LOW -> {
                Toast.makeText(context, "Battery low", Toast.LENGTH_LONG).show()
            }

            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                val state = intent.getBooleanExtra("state", false)
                Toast.makeText(context, "Airplane mode turned on: $state", Toast.LENGTH_LONG).show()
            }
            ACTION_CLICKED -> {
                val count = intent.getIntExtra(EXTRA_COUNT, 0)
                Toast.makeText(context, "Button clicked $count time(s).", Toast.LENGTH_SHORT).show()
            }
            ACTION_LOADED -> {
                val percent = intent.getIntExtra(EXTRA_PERCENT, 0)
                Toast.makeText(context, "Loaded $percent%...", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    companion object {
        const val ACTION_CLICKED = "clicked"
        const val EXTRA_COUNT = "count"
        const val ACTION_LOADED = "loaded"
        const val EXTRA_PERCENT = "percent"
    }
}