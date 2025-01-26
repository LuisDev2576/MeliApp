package com.luis.dev.meliapp.features.results.domain.usecases

import SearchResultItem

interface GetResultsUseCase {
    suspend fun execute(query: String): List<SearchResultItem>
}