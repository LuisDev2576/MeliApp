package com.luis.dev.meliapp.features.results.data.datasource

import SearchResponse
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Implementación de [ResultsDataSource] que utiliza Ktor para realizar llamadas a la API de Mercado Libre.
 *
 * @param httpClient Cliente HTTP utilizado para realizar solicitudes a la API.
 */
class ResultsDataSourceImpl(
    private val httpClient: HttpClient
) : ResultsDataSource {

    companion object {
        private const val TAG = "ResultsDataSourceImpl"
    }

    /**
     * Realiza una solicitud a la API de Mercado Libre para buscar artículos basados en un sitio y una consulta.
     *
     * @param siteId Identificador del sitio donde se realizará la búsqueda (por ejemplo, "MLA" para Argentina).
     * @param query Palabra clave o frase que define la búsqueda de los artículos.
     * @return [SearchResponse] con los resultados de la búsqueda.
     */
    override suspend fun searchItems(siteId: String, query: String): SearchResponse {
        Log.d(TAG, "Making API call to search items with siteId: $siteId and query: $query")
        val response = try {
            httpClient.get("https://api.mercadolibre.com/sites/$siteId/search") {
                parameter("q", query)
            }
        } catch (e: Exception) {
            Log.e(TAG, "API call failed", e)
            throw e
        }

        val responseBody: SearchResponse = response.body()
        Log.d(TAG, "API call successful. Number of items received: ${responseBody.results.size}")
        return responseBody
    }
}