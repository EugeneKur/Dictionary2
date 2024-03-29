package ru.geekbrains.dictionary

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.geekbrains.dictionary.di.application
import ru.geekbrains.dictionary.di.historyScreen
import ru.geekbrains.dictionary.di.mainScreen

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}