package com.kes.app044_kt_services.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log


class MyIntentJobSchedulerService : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 0 until 3) {
            Thread.sleep(1000)
            log("Timer: $page : $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyIntentJobSchedulerService: $message")
    }

    companion object {
        private const val NAME = "MyIntentJobSchedulerService"
        private const val PAGE = "page"

        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyIntentJobSchedulerService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}