package com.mobilefolk.test

import android.app.Application
import com.mobilefolk.test.core.di.*
import com.mobilefolk.test.core.utils.DebugTree
import com.mobilefolk.test.core.utils.NotLoggingTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    helperModule,
                    retrofitModule,
                    serviceModule,
                    dataSourceModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
        if (!BuildConfig.DEBUG) {
            Timber.plant(NotLoggingTree())
        } else {
            Timber.plant(DebugTree())
        }
    }
}
