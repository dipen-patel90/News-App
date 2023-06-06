package com.ct

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.ct.api.Environment
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp : Application() {

    companion object {
        var currentEnvironment = Environment.fromBuildFlavor(BuildConfig.FLAVOR)
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}