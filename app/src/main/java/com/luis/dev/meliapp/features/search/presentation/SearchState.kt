package com.luis.dev.meliapp.features.search.presentation

import SearchResultItem
/**
 * Representa el estado de la pantalla de búsqueda.
 * @param query El texto actual en el campo de búsqueda.
 * @param isLoading Indica si se está realizando una búsqueda en segundo plano.
 * @param results Lista de resultados obtenidos.
 * @param error Mensaje de error en caso de que ocurra alguna falla.
 */
data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: List<SearchResultItem> = emptyList(),
    val error: String? = null
)
