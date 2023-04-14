package com.julian.taipeitour

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import kotlin.properties.Delegates

@HiltAndroidApp
class BaseApplication: Application() {
    companion object {
        var instance: BaseApplication by Delegates.notNull()
        private val TAG: String = BaseApplication::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "init application")
        instance = this
    }
}