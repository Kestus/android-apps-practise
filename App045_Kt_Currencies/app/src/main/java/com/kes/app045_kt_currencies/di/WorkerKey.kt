package com.kes.app045_kt_currencies.di

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)
