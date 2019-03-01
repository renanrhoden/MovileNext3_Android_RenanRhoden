package com.renanrhoden.wheretolunch

import android.app.Application
import com.renanrhoden.wheretolunch.injection.KoinModule
import org.koin.android.ext.android.startKoin

class WhereToLunchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(KoinModule().getModule(this)))
    }
}