package com.example.vkfeed

import androidx.multidex.MultiDexApplication
import com.example.vkfeed.di.ApplicationContainer
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.DependencyProvider
import timber.log.Timber
import kotlin.reflect.KClass
import kotlin.system.exitProcess


class VKFeedApplication : MultiDexApplication(), DependencyProvider {

    override fun onCreate() {
        super.onCreate()
        container = ApplicationContainer(this)
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            Timber.e(e)
            exitProcess(0)
        }
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override var container: DependencyContainer? = null

}