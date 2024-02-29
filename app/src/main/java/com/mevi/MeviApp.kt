package com.mevi

import android.app.Application
import com.mevi.di.appModule
import com.mevi.di.dataModule
import com.mevi.di.domainModule
import com.mevi.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MeviApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MeviApp)
            androidLogger()
            modules(appModule)
            modules(dataModule)
            modules(domainModule)
            modules(uiModule)
        }
    }
}