package com.luis.dev.meliapp.features.search.data.repository

import SearchResponse
import com.luis.dev.meliapp.features.search.data.datasource.SearchDataSource
import android.util.Log

class SearchRepositoryImpl(
    private val dataSource: SearchDataSource,
    private val defaultSiteId: String = "MLA"
) : SearchRepository {

    companion object {
        private const val TAG = "SearchRepositoryImpl"
    }

    override suspend fun searchItems(query: String): SearchResponse {
        Log.d(TAG, "Searching items with query: $query and siteId: $defaultSiteId") // Log de llamada al datasource
        val response = dataSource.searchItems(defaultSiteId, query)
        Log.d(TAG, "Received response with ${response.results.size} items from datasource") // Log de respuesta del datasource
        return response
    }
}
