package com.kes.app046_kt_broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    private var count = 0
    private lateinit var progressBar: ProgressBar

    // localize broadcasts to only this application
    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == MyReceiver.ACTION_LOADED) {
                val percent = intent.getIntExtra(MyReceiver.EXTRA_PERCENT, 0)
                progressBar.progress = percent
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button = findViewById<Button>(R.id.btn_broadcast)
        button.setOnClickListener {
            val intent = Intent(MyReceiver.ACTION_CLICKED)
            intent.putExtra(MyReceiver.EXTRA_COUNT, ++count)
            localBroadcastManager.sendBroadcast(intent)
        }

        progressBar = findViewById(R.id.progressBar)

        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(MyReceiver.ACTION_CLICKED)
            addAction(MyReceiver.ACTION_LOADED)
        }
//        ContextCompat.registerReceiver(
//            this,
//            receiver,
//            intentFilter,
//            ContextCompat.RECEIVER_NOT_EXPORTED
//        )

        localBroadcastManager.registerReceiver(receiver, intentFilter)

        Intent(this, NetworkService::class.java).apply {
            startService(this)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager.unregisterReceiver(receiver)
    }
}