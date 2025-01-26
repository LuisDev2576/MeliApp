package com.luis.dev.meliapp

import android.app.Application
import com.luis.dev.meliapp.di.globalModule
import com.luis.dev.meliapp.features.details.di.detailsModule
import com.luis.dev.meliapp.features.results.di.resultsModule
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
                    resultsModule,
                    detailsModule,
                    globalModule
                )
            )
        }
    }
}