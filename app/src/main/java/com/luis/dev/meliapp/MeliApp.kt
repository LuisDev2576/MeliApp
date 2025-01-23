package com.luis.dev.meliapp

import android.app.Application
import com.luis.dev.meliapp.di.globalModule
import com.luis.dev.meliapp.features.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MeliApp : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MeliApp)
            modules(
                listOf(
                    searchModule,
                    globalModule
                )
            )
        }
    }
}