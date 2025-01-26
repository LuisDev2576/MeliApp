package com.luis.dev.meliapp.features.results.data.datasource

import SearchResponse
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.json.Json

class ResultsDataSourceImpl(
    private val httpClient: HttpClient
) : ResultsDataSource {
    companion object {
        private const val TAG = "SearchDataSourceImpl"
    }

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