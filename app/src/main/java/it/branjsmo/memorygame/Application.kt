package it.branjsmo.memorygame

import android.app.Application
import it.branjsmo.memorygame.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GameApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GameApplication)
            androidLogger(level = Level.DEBUG)
            modules(appModule)
        }
    }
}