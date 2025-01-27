package com.luis.dev.meliapp.di

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
 * Módulo de dependencias globales utilizado para inyección de dependencias con Koin.
 */
val globalModule = module {

    /**
     * Proveedor de configuración de Kotlinx Serialization para deserializar JSON.
     * - Configuración: Ignora claves desconocidas en las respuestas JSON.
     */
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

    /**
     * Proveedor de [HttpClient] configurado con el motor OkHttp y soporte para:
     * - Serialización JSON con Kotlinx Serialization.
     * - Logs de nivel BODY para depuración de solicitudes y respuestas HTTP.
     */
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(get<Json>())
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }

    /**
     * Proveedor de [SearchBarViewModel] para gestionar el estado y lógica de la barra de búsqueda.
     */
    viewModel {
        SearchBarViewModel()
    }
}
