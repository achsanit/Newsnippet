package com.achsanit.newsnippet.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

object KoinInitializer {

    fun init(app: Application) {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(app)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    apiModule,
                    repositoryModule
                )
            )
        }
    }
}