package com.achsanit.newsnippet.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.achsanit.newsnippet.di.KoinInitializer

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // start koin
        KoinInitializer.init(this)
    }
}