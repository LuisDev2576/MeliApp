package com.luis.dev.meliapp.features.search.domain.usecases

import SearchResultItem
import com.luis.dev.meliapp.features.search.data.repository.SearchRepository
import android.util.Log

class SearchUseCaseImpl(
    private val repository: SearchRepository
) : SearchUseCase {

    companion object {
        private const val TAG = "SearchUseCaseImpl"
    }

    override suspend fun execute(query: String): List<SearchResultItem> {
        Log.d(TAG, "Executing SearchUseCase with query: $query") // Log de ejecución
        val response = repository.searchItems(query)
        Log.d(TAG, "Received ${response.results.size} results from repository") // Log de respuesta
        return response.results
    }
}
