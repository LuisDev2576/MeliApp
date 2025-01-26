package com.luis.dev.meliapp.features.results.data.repository

import SearchResponse
import com.luis.dev.meliapp.features.results.data.datasource.ResultsDataSource

class ResultsRepositoryImpl(
    private val dataSource: ResultsDataSource,
    private val defaultSiteId: String = "MLA"
) : ResultsRepository {

    override suspend fun searchItems(query: String): SearchResponse {
        return dataSource.searchItems(defaultSiteId, query)
    }
}