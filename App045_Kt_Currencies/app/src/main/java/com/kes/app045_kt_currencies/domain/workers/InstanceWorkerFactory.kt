package com.kes.app045_kt_currencies.domain.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface InstanceWorkerFactory {
    fun create(
        context: Context,
        workerParameters: WorkerParameters
    ): ListenableWorker
}