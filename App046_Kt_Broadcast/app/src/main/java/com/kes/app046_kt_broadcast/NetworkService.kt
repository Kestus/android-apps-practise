package com.kes.app046_kt_broadcast

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.concurrent.thread

class NetworkService: Service() {

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            for (i in 1 until 11) {
                Thread.sleep(1000)
                Intent(MyReceiver.ACTION_LOADED).apply {
                    putExtra(MyReceiver.EXTRA_PERCENT, i * 10)
                    localBroadcastManager.sendBroadcast(this)
                }

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}