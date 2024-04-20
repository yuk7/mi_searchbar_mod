package io.github.yuk7.miuisearchbar

import android.app.Application
import android.util.Log
import androidx.annotation.Keep
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    @Keep
    fun getFrameworkVersion(): Int? {
        // This method is used to get the version of the hook framework
        // The actual value is injected in the hook that works in the class loader
        return null
    }

    companion object {
        lateinit var instance: MainApplication private set
    }
}