package com.luis.dev.meliapp.features.results.data.datasource

import SearchResponse

interface ResultsDataSource {
    suspend fun searchItems(siteId: String, query: String): SearchResponse
}