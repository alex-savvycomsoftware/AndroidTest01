package com.savvycom

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.savvycom.di.module.appModule
import com.savvycom.di.module.repoModule
import com.savvycom.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}