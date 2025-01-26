package com.luis.dev.meliapp.features.results.data.repository

import SearchResponse

interface ResultsRepository {
    suspend fun searchItems(query: String): SearchResponse
}