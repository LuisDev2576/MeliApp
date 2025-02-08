package com.luis.dev.meliapp.features.results.presentation

import SearchResultItem

data class ResultsState(
    val isLoading: Boolean = false,
    val results: List<SearchResultItem> = emptyList(),
    val error: String? = null
)