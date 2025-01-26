package com.luis.dev.meliapp.features.results.domain.usecases

import SearchResultItem
import com.luis.dev.meliapp.features.results.data.repository.ResultsRepository

class GetResultsUseCaseImpl(
    private val repository: ResultsRepository
) : GetResultsUseCase {

    override suspend fun execute(query: String): List<SearchResultItem> {
        return repository.searchItems(query).results
    }
}