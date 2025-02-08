package com.luis.dev.meliapp

import android.app.Application
import com.luis.dev.meliapp.di.GlobalModule
import com.luis.dev.meliapp.features.authentication.di.AuthenticationModule
import com.luis.dev.meliapp.features.details.di.DetailsModule
import com.luis.dev.meliapp.features.results.di.ResultsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MeliApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MeliApp)
            modules(
                listOf(
                    AuthenticationModule,
                    ResultsModule,
                    DetailsModule,
                    GlobalModule
                )
            )
        }
    }
}