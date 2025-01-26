package com.luis.dev.meliapp.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.luis.dev.meliapp.core.components.searchTopAppBar.SearchBarViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

/**
 * Módulo con dependencias globales, utilizadas por múltiples features.
 */
val globalModule = module {

    // Proveedor de Json de Kotlinx Serialization
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

    // Proveedor de HttpClient de Ktor con OkHttp como motor
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(get<Json>())
            }
            install(Logging) {
                level = LogLevel.BODY
            }
            // Configuraciones extras de Ktor (timeouts, etc.)
        }
    }

    viewModel {
        SearchBarViewModel()
    }
}
